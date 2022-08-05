package com.example.selfPrj.config.auth;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		String password = (String) request.getParameter("password");
		System.out.println("---------->? 비밀번호: " +password);
		
		String errorMsg;
		if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
			errorMsg = "아이디 또는 비밀번호 오류";
		} else if (exception instanceof UsernameNotFoundException) {
			errorMsg = "존재하지 않는 아이디";
		} else {
			errorMsg = "알 수 없는 오류";
		}
		
		errorMsg = URLEncoder.encode(errorMsg, "UTF-8"); //한글 인코딩 깨지는 문제 방지
		setDefaultFailureUrl("/loginForm?error=true?exception="+ errorMsg); //URL 파라미터 실어주기
		super.onAuthenticationFailure(request, response, exception);
	}
}
