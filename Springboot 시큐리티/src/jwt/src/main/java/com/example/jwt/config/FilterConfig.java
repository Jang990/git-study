package com.example.jwt.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.jwt.filter.MyFilter1;
import com.example.jwt.filter.MyFilter2;

@Configuration
public class FilterConfig {
	
	@Bean
	public FilterRegistrationBean<MyFilter1> filter1() {
		FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
		bean.addUrlPatterns("/*"); // 모든 요청 
		bean.setOrder(0); // 우선순위를 가장 높게(0)설정(낮은 번호가 필터중 가장 먼저 실행)
		return bean; 
	}
	
	@Bean
	public FilterRegistrationBean<MyFilter2> filter2() {
		FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
		bean.addUrlPatterns("/*"); // 모든 요청 
		bean.setOrder(1); // 우선순위를 가장 높게(0)설정(낮은 번호가 필터중 가장 먼저 실행)
		return bean; 
	}
}
