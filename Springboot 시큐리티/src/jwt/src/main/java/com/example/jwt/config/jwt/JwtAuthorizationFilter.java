package com.example.jwt.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt.config.auth.PrincipalDetails;
import com.example.jwt.model.User;
import com.example.jwt.repository.UserRepository;

//시큐리티가 filter를 가지고있는데 그 필터중 BasicAuthenticationFilter 라는 것이 있다.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private UserRepository userRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}
	
	//이 필터를 탄다. - 토큰을 확인하는 로직을 짜면 된다.
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//super.doFilterInternal(request, response, chain); // super를 지워라. 이거 안지우면 아래도 doChain이 있어서 오류난다.
		System.out.println("JwtAuthorizationFilter를 탄다");
		String jwtHeader = request.getHeader("Authorization");
		System.out.println("jwtHeader: " + jwtHeader);
		
		
		//토큰이 없거나 다름 - 아래 로직을 진행하지 않고 다른 필터를 계속 타도록 넘김
		if(jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
			chain.doFilter(request, response);
			return;
		}
		
		//JWT 토큰을 검증해서 정상적인 사용자인지 확인
		String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
		//HMAC은 대칭키이다. 공개키-개인키가 아니다. 그래서 같은 "cos"라는 문자 사용
		String username = JWT.require(Algorithm.HMAC512("cos")).build() 
				.verify(jwtToken) //서명 
				.getClaim("username").asString(); //username 가져오기
		
		//서명이 정상적으로 됨
		if(username != null) {
			User userEntity = userRepository.findByUsername(username);
			PrincipalDetails principalDetails = new PrincipalDetails(userEntity);
			
			/*
			 * Authentication객체를 만들 것이다.
			 * Authentication authentication = authenticationManager.authenticate(authenticationToken);
			 * 이 방식은 PrincipalDetailsService에 loadUserByUsername() 메서드를 실행하는데
			 * 이렇게 만드는건 실제로 로그인을 정상적으로 진행해서 만들어지는 Authentication 객체이다.
			 * 
			 * 이 방식이 아니라 Authentication 임의로 만드려면 아래와 같이 만들어주면된다.
			 * new UsernamePasswordAuthenticationToken(principalDetails객체, 패스워드, 권한);
			 * 하지만 우린 임의로 만들 것이기 때문에 패스워드부분은 null로 만들어준다. 
			 * - 이렇게 만들어주는 근거는 username이 null이 아니기 때문이다. 즉 인증이 되었기 때문이다.
			 * 
			 * 정리하자면 정상적인 로그인 처리과정은 아니다.
			 * Jwt 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어 주는 것이다.
			 */
			Authentication authentication = 
					new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
			
			//강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
			SecurityContextHolder.getContext()//시큐리티 세션공간
				.setAuthentication(authentication);
			System.out.println("정상로그인 처리");
			doFilter(request, response, chain); //다른 필터 타기
		}
		
	}
	
}
