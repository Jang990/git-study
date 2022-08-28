package com.example.jwt.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Filter;

public class MyFilter3 implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//다운캐스팅
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		if(req.getMethod().equals("POST")) {
			System.out.println("POST 요청됨");
			String headerAuth = req.getHeader("Authorization");
			if(headerAuth.equals("cos")) {
				chain.doFilter(req, res); //다운캐스팅한것을 체인에 넘김
			} else {
				PrintWriter out = res.getWriter();
				out.println("인증안됨");
			}
		}
		chain.doFilter(req, res); //내가 넣은 코드
	}

}
