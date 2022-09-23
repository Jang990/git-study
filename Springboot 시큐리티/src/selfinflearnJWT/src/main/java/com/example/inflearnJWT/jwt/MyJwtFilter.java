package com.example.inflearnJWT.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

// 2
//JwtSecurityConfig에서 필터를 걸 것이기 때문에 지멋대로 필터가 걸리지 않도록 @Component 어노테이션을 추가하지 않는다.
public class MyJwtFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(MyJwtFilter.class);
	public static final String AUTHORIZATION_HEADER = "Authorization";

	//TokenProvider는 JwtSecurityConfig에서 생성자로 주입하기 때문에 @Autowired같은 주입이 필요없음
	private MyJwtProvider tokenProvider;
	public MyJwtFilter(MyJwtProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	// JWT 토큰의 인증정보를 SecurityContext에 저장하는 역할
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request; // 둘의 차이 - https://ckddn9496.tistory.com/52
		//ServletRequest 타입인 request는 .getHeader(...) 불가능
		
		String jwt = resolveToken(httpServletRequest);
		String requestURI = httpServletRequest.getRequestURI();
		
		if(StringUtils.hasText(jwt) 
				&& tokenProvider.validateToken(jwt)) { // 토큰 유효성 검사
			Authentication authentication = tokenProvider.getAuthentication(jwt);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
		} else {
			logger.debug("유효한 JWT 토큰이 없습니다, uri: {}", requestURI);
		}
		
		filterChain.doFilter(request, response);
	}
	
	// Request Header에서 토큰정보를 꺼내오기 위한 메소드
	private String resolveToken(HttpServletRequest httpServletRequest) {
		String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
		
		if(StringUtils.hasText(bearerToken) // 실제 텍스트가 포함되어 있는지 확인. 즉 문자열이 null이 아니고 길이가 0보다 크고 공백이 아닌 문자가 하나 이상 포함된 경우 true
				&& bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7); // Bearer 문자열 제거
		}
		
		return null;
	}
	
}
