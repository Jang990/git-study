package com.example.jwt.config.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
		//2. authenticationManager로 로그인 시도 -> PrincipalDetailsService에 loadUserByUsername() 메서드 실행됨
		return super.attemptAuthentication(request, response);
	}
}
