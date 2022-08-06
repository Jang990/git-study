package com.example.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		
		// 내 서버가 응답할 때 json을 자바 스크립트에서 처리할 수 있게 설정
		// false로 한다면 axios, ajax등등 자바스크립트에서 요청을 해도 응답이 오지 않는다.
		config.setAllowCredentials(true); 
		
		
		config.addAllowedOrigin("*"); // 모든 IP 응답 허용
		config.addAllowedHeader("*"); // 모든 헤더 응답 허용
		config.addAllowedMethod("*"); // GET, POST, PUT 등등의 요청을 다 허용
		
		// "/api/**" 로 들어오는 모든 요청은 config를 따른다
		source.registerCorsConfiguration("/api/**", config); 
		return new CorsFilter(source);
	}
}
