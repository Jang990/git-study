package com.example.inflearnJWT.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class MyJwtProvider {

	private final Logger logger = LoggerFactory.getLogger(MyJwtProvider.class);
	private static final String AUTHORITIES_KEY = "auth";
	private final String secret;
	private final long tokenValidityInMilliseconds;
	private Key key;

	/*
	 * jwt: 
	 * 	header: Authorization 
	 * 	#HS512 알고리즘을 사용할 것이기 때문에 512bit, 즉 64byte 이상의secret key를 사용해야 한다. 
	 * 	secret: c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK
	 * 	token-validity-in-seconds: 86400
	 */
	//jwt 설정 정보들 가져와서 지역변수들 설정해주기
	public MyJwtProvider(@Value("${jwt.secret}")String secret, @Value("${jwt.token-validity-in-seconds}")long tokenSecond) {
		//필드에 @Value를 붙혀도 상관없을 거 같다. 하지만 *1000을 하고있기 때문에 일단은 놔둔다.
		//생성자 호출시에 클래스에 @Value를 붙인 필드는 null이다. 하지만 @PostConstruct 메소드가 실행될 때는 세팅이 되어있다. - https://scshim.tistory.com/357
		this.secret = secret;
		this.tokenValidityInMilliseconds = tokenSecond * 1000;
	}

	// 1. implements InitializingBean하고 afterPropertiesSet를 Override한 이유
	// 빈 생성되고 주입 받은 후 secret 값을 Base64 Decode해서 Key변수에 할당하기 위함이다. 	
	// 생성자 -> PostConstruct -> ...
	@PostConstruct
	public void init() {
		//생성자로 가져온 값들을 바탕으로 key값 세팅해주기
		byte[] keyBytes = Decoders.BASE64.decode(secret);
		key = Keys.hmacShaKeyFor(keyBytes);
	}

	// 2. Authentication 객체의 권한 정보를 이용해서 토큰을 생성
	public String createToken(Authentication authentication) {
		/*
		 * createToken을 하기 이전에 login을 시도하는 사용자가 컨트롤러로 들어오고
		 * loadUserByUsername를 통해서 인증을 성공하면
		 * SecurityContextHolder에 authentication을 세팅한다.
		 * 그리고 createToken을 이용해 JWT 토큰을 만들게 된다.
		 */
		
		//사용자 권한을 USER,ADMIN 이런식으로 변경하는 듯
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)//권한 String으로 변경
				.collect(Collectors.joining(","));
		
		//만료시간 객체 설정
		long now = (new Date()).getTime();
		Date validity = new Date(now + this.tokenValidityInMilliseconds);
		
		//토큰 생성
		//builder는 토큰을 생성할 때 parserBuilder는 토큰을 읽을 때
		return Jwts.builder()
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(validity)
				.compact(); //JWT Compact Serialization 규칙에 따라 URL로 사용할 수 있도록 JWT를 직렬화합니다.
	}

	// 3. 토큰을 파라미터로 받아 토큰에 담겨있는 권한정보를 이용해서 Authentication 객체를 리턴
	public Authentication getAuthentication(String token) {
		/*
		 * 만약 토큰이 있다면 토큰에 해당하는 세션을 세팅해주어야 함
		 * 그래서 Authentication객체를 만들어고 필터에서 사용하도록 넘김
		 */
		Claims claims = Jwts
				.parserBuilder()
				.setSigningKey(key) // JWS(토큰이 아닌 string형식) 서명 검증을 위한 SecretKey
				.build() // 스레드에 안전한 JwtPaser를 리턴하기 위해 JwtPaserBuilder의 build()메서드를 호출
				.parseClaimsJws(token) // JWS 문자열을 구문 분석하고 결과 Claims JWS 인스턴스를 반환
				.getBody(); // 내가 생성한 토큰에 설정들을 가져옴
		//https://samtao.tistory.com/65 참고
		
		//권한정보 뽑아내기
		Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		
		//왜 pw부분을 "" 줄까? - password는 db에 암호화되어 저장되어 있기 때문이다.
		User principal = new User(claims.getSubject(), "", authorities);
		
		//pw는 알 수 없기 때문에 토큰으로 pw를 주어 임의로 만드는 것이다.
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	// 4. 토큰의 유효성 검증을 수행
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			logger.info("잘못된 JWT 서명입니다.");
		} catch (ExpiredJwtException e) {
			logger.info("만료된 JWT 토큰입니다.");
		} catch (UnsupportedJwtException e) {
			logger.info("지원되지 않는 JWT 토큰입니다.");
		} catch (IllegalArgumentException e) {
			logger.info("JWT 토큰이 잘못되었습니다.");
		}
		
		return false;
	}
}
