package com.example.inflearnJWT.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

//`AuthenticationEntryPoint`는 인증이 되지않은 유저가 요청을 했을때 동작된다.
@Component //어노테이션 까먹지마라
public class MyJwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	// Response에 401이 떨어질만한 에러가 발생할 경우 commence라는 메소드를 실행 - 정확하진 않음
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		// 유효한 자격증명을 제공하지 않고 접근하려 할때 401
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

}
