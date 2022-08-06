package com.example.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Filter;

public class MyFilter1 implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("필터 1");
		chain.doFilter(request, response);
		/*
		 * 만약 위와 같은 설정을 적지 않고
		 * PrintWriter out = response.getWriter();
		 * out.print("안녕");
		 * 이런 코드만 작성했다면 "안녕"을 찍고 프로그램이 끝나버린다. 
		 * 그러니 끝나지말고 프로세스를 계속 진행하라고 chain에 넘겨줘야 한다.
		 */
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		if(req.getMethod().equals("POST")) {
			System.out.println("포스트 요청됨");
			String headerAuth = req.getHeader("Authorization");
			System.out.println(headerAuth);
		}
	}

}
