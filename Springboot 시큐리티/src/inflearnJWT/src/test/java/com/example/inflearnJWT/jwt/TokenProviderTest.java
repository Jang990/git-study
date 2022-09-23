package com.example.inflearnJWT.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.inflearnJWT.dto.LoginDto;

@SpringBootTest
class TokenProviderTest {

	@Autowired
	private TokenProvider tokenProvider;
	@Autowired
	private AuthenticationManagerBuilder authenticationManagerBuilder;

	Authentication createTestAuthentication() {
		LoginDto loginDto = new LoginDto("admin", "admin");

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());

		// Authentication 객체 생성 -> CustomUserDetailsService에 loadUserByUsername 메서드 실행
		return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
	}

	@Test
	@DisplayName("토큰 생성 테스트")
	String testCreateToken() {

		// Authentication 객체 생성 -> CustomUserDetailsService에 loadUserByUsername 메서드 실행
		Authentication authentication = createTestAuthentication();
		String jwt = tokenProvider.createToken(authentication);
		System.out.println("=======Test 토큰 생성:" + jwt);
		return jwt;
	}

	@Test
	@DisplayName("Authentication 객체 리턴 테스트")
	void testGetAuthentication() {
		String jwt = testCreateToken();
		Authentication authentication = tokenProvider.getAuthentication(jwt);
		System.out.println("=======Test 객체 리턴: " + authentication.getName());
	}

	@Test
	void testValidateToken() {
		String jwt = testCreateToken();
//		tokenProvider.validateToken(jwt);
		//토큰변조
		tokenProvider.validateToken(jwt+"Hello");
	}

}
