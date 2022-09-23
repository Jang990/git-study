package com.example.inflearnJWT.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 3
//TokenProvider를 SecurityConfig에 적용
public class MyJwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
	//SecurityConfigurerAdapter는 하위 클래스가 관심 있는 메서드만 구현할 수 있도록 해준다.
	private MyJwtProvider myTokenProvider;

	//SecurityConfig부분에서 생성자로 주입받기 때문에 @Autowied 필요없음
	public MyJwtSecurityConfig(MyJwtProvider tokenProvider) {
        this.myTokenProvider = tokenProvider;
    }

	@Override
	public void configure(HttpSecurity http) {
		http.addFilterBefore(
				new MyJwtFilter(myTokenProvider), 
				UsernamePasswordAuthenticationFilter.class
		);
	}
}
