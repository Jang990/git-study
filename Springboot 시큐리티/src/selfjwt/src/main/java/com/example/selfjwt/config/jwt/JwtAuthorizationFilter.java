package com.example.selfjwt.config.jwt;

import java.io.IOException;
import java.util.Optional;

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
import com.example.selfjwt.model.UserModel;
import com.example.selfjwt.repository.UserRepository;

//이 필터는 어떤 URL이든 타게 된다.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter{
	
	private final UserRepository userRepository;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
		super(authenticationManager);
		this.userRepository = userRepository;
	}
	
	//사용자의 토큰 확인 후 시큐리티 세션에 사용자정보 저장
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("===============>"+"2번 JWT 필터");
		String jwtHeader = request.getHeader("Authorization");
		
		//헤더 확인
		if(jwtHeader == null || !jwtHeader.startsWith("Bearer ")) {
			chain.doFilter(request, response);
			return;
		}
		
		//토큰 뜯어보기
		String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
		String username = JWT.require(Algorithm.HMAC512("cos")).build()
				.verify(jwtToken)
				.getClaim("username").asString();
		
		//토큰내용이 없음
		if(username == null) {
			chain.doFilter(request, response);
			return;
		}
		
		Optional<UserModel> userCheck = userRepository.findByUsername(username);
		//DB에 없음
		if(userCheck.isEmpty()) {
			chain.doFilter(request, response);
			return;
		}
		
		//정상처리
		UserModel user = userCheck.get();
		UsernamePasswordAuthenticationToken token = 
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
		SecurityContextHolder.getContext().setAuthentication(token);
		System.out.println("===============>"+"세션만들기");
		chain.doFilter(request, response);
	}

}
