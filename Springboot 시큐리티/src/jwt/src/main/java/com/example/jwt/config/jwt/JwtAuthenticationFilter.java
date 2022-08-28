package com.example.jwt.config.jwt;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt.config.auth.PrincipalDetails;
import com.example.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

/*
스프링 시큐리티에 UsernamePasswordAuthenticationFilter 가 있다
'/login' 요청해서 username,password 전송(post)
UsernamePasswordAuthenticationFilter가 동작을 한다
하지만 formLogin을 disable 횄기 때문에 동작하지 않는다. 그래서 시큐리티 필터에 달아주어야 한다.
*/
@RequiredArgsConstructor //자동으로 멤버 변수를 받는 생성자를 만듦
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;
	
	//이 메서드는 '/login' 요청을 하면 로그인 시도를 위해서 실행되는 메서드
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("JwtAuthenticationFilter: 로그인 시도중");
		//1. username, password 받기
		try {
			/*
			BufferedReader br = request.getReader();
			String input = null;
			while((input = br.readLine()) != null) {
				System.out.println(input);
			}
			//출력결과: "username=ssar&password=1234"
			*/
			
			ObjectMapper om = new ObjectMapper();
			User user = om.readValue(request.getInputStream(), User.class);
			System.out.println(user);
			//출력결과: "User(id=0, username=ssar, password=1234, roles=null)"
			
			//2. authenticationManager로 로그인 시도 -> PrincipalDetailsService에 loadUserByUsername() 메서드 실행됨
			//첫번째는 username, 두번째는 password를 넣어주면 된다
			UsernamePasswordAuthenticationToken authenticationToken = 
					new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
			
			//여기서 토큰을 넣어주면 PrincipalDetailsService에 loadUserByUsername() 함수가 실행된다.
			//password는 스프링이 따로 처리를 해준다. 내부적인것은 잘 몰라도 된다
			//loadUserByUsername() 메서드가 실행된 후 정상이면 Authentication이 리턴된다.
			Authentication authentication = authenticationManager.authenticate(authenticationToken);
			
			//로그인 확인
			PrincipalDetails details = (PrincipalDetails) authentication.getPrincipal();
			System.out.println(details.getUser().getUsername());
			
			//Authentication 객체가 session영역에 저장된다.
			return authentication; //객체 반환
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null; // 로그인 실패로 null 리턴
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
		
		//빌더 패턴이다.
		String jwtToken = JWT.create()
				.withSubject(principalDetails.getUsername()) // 토큰의 이름 - 크게 의미없음
				.withExpiresAt( //만료 시간 - 토큰의 유효시간. 토큰이 탈취돼도 안전하도록 좀 짧게주는게 좋음.
						new Date(System.currentTimeMillis()+(60000*10)) //현재시간 + 유효시간(10분)
					)  
				.withClaim("id", principalDetails.getUser().getId()) //비공개 클레임. 넣고싶은 키 벨류값 막 넣으면 된다.
				.withClaim("username", principalDetails.getUser().getUsername())
				.sign(Algorithm.HMAC512("cos")); //HMAC 방식
		
		//사용자에게 전달
		response.addHeader("Authorization", "Bearer "+ jwtToken); //"Bearer "에 한칸 뛰는거 잊지마라 
	}
}
