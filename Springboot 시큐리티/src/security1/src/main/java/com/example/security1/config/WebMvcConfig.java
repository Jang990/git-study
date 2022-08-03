package com.example.security1.config;

import org.springframework.boot.web.servlet.view.MustacheViewResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		MustacheViewResolver resolver = new MustacheViewResolver();
		resolver.setCharset("UTF-8"); // 내가 만드는 뷰의 인코딩은 기본적으로 UTF-8
		resolver.setContentType("text/html; charset=UTF-8"); // 내가 너한테 던지는 파일은 html파일에 UTF-8
		resolver.setPrefix("classpath:/templates/"); // classpath: 까지가 너의 프로젝트 경로
		resolver.setSuffix(".html");
		
		registry.viewResolver(resolver); // 생성한 뷰 리졸버 등록
	}
}
