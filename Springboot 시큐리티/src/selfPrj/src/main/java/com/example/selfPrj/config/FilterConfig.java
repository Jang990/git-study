package com.example.selfPrj.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.selfPrj.filter.MyFilter2;
import com.example.selfPrj.filter.MyFilter3;

@Configuration
public class FilterConfig {
	
	@Bean
	public FilterRegistrationBean<MyFilter2> myFilter2() {
		FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<MyFilter2>(new MyFilter2());
		bean.addUrlPatterns("/*");
		bean.setOrder(0);
		return bean;
	}
	
	@Bean
	public FilterRegistrationBean<MyFilter3> myFilter3() {
		FilterRegistrationBean<MyFilter3> bean = new FilterRegistrationBean<MyFilter3>(new MyFilter3());
		bean.addUrlPatterns("/*");
		bean.setOrder(1);
		return bean;
	}
	
}
