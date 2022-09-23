package com.example.inflearnJWT.config;

import org.springframework.context.annotation.Bean; 
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.example.inflearnJWT.jwt.MyJwtAccessDeniedHandler;
import com.example.inflearnJWT.jwt.MyJwtAuthenticationEntryPoint;
import com.example.inflearnJWT.jwt.MyJwtProvider;
import com.example.inflearnJWT.jwt.MyJwtSecurityConfig;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class MySecurityConfig {
	private final MyJwtProvider tokenProvider;
	private final CorsFilter corsFilter;
	private final MyJwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final MyJwtAccessDeniedHandler jwtAccessDeniedHandler;

	// 사용할 암호화 객체
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//해당 주소로 들어오면 필터 적용 제외
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
		//WebSecurityCustomizer는 WebSecurity 사용자 지정을 위한 콜백 인터페이스
        return (web) -> web.ignoring().antMatchers("/h2-console/**"
                , "/favicon.ico"
                , "/error");
    }
	
	//필터체인 정의
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
        		// token을 사용하는 방식이기 때문에 csrf를 disable
        		.csrf().disable()
                
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                
                .exceptionHandling() //예외 핸들링부분
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                
                // enable h2-console (h2 콘솔을 위한 설정)
                .and()
                .headers()
                .frameOptions()
                .sameOrigin() // 동일 도메인에서는 iframe 접근이 가능하도록 X-Frame-Options를 sameOrigin()으로 설정
                
                // 세션을 사용하지 않기 때문에 STATELESS로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                
                .and()
                .authorizeRequests()
                .antMatchers("/api/hello").permitAll()
                //로그인, 회원가입은 토큰이 없는 상태에서 이뤄지기 때문에 다음과 같이 설정
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers("/api/signup").permitAll() 

                .anyRequest().authenticated()

                .and()
                .apply(new MyJwtSecurityConfig(tokenProvider)); // JwtFilter 적용
        
        return httpSecurity.build();
	}
}
