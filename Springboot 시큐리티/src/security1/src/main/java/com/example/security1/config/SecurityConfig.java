package com.example.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록이 된다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeHttpRequests() // 어떤 request가 들어온다면.
			.antMatchers("/user/**").authenticated() // /user/** 이런주소로 들어오면 인증이 필요해
			.antMatchers("/manager/**").hasRole("ROLE_ADMIN or ROLE_MANAGER") // /manager/** 이런 주소로 들어오면 인증뿐만 아니라 "ROLE_ADMIN or ROLE_MANAGER" 권한이 있는 사람만 들어오게 할거야 
			.antMatchers("/admin/**").hasRole("ROLE_ADMIN")
			.anyRequest().permitAll() // 다른 모든 request는 권한 허용.
			/* -----이 코드들은 권한없는 접근을 로그인페이지로 이동시키는 코드들 -----*/
			.and()
			.formLogin()
			.loginPage("/loginForm");
			/* --------------------*/
	}
}
