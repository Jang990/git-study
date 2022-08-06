package com.example.jwt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.example.jwt.filter.MyFilter1;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity // 이 시큐리티설정 활성화
@RequiredArgsConstructor //롬복 어노테이션이다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final CorsFilter corsConfig;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//세션을 사용하지 않겠다.
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.httpBasic().disable();// 기본적인 http 로그인 방식을 아예 안쓴다.
		http.formLogin().disable();// 폼태그를 만들어서 로그인하는걸 안쓴다.
		
		http.authorizeRequests()
			.antMatchers("/api/v1/user/**").hasAnyRole("USER", "MANAGER", "ADMIN")
			.antMatchers("/api/v1/manager/**").hasAnyRole("MANAGER", "ADMIN")
			.antMatchers("/api/v1/admin/**").hasAnyRole("ADMIN")
			.anyRequest().permitAll();
		
		http.addFilter(corsConfig);
//		http.addFilterBefore(new MyFilter1(), BasicAuthenticationFilter.class);
		
		http.csrf().disable();
	}
}
