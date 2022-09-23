package com.example.inflearnJWT.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inflearnJWT.dto.LoginDto;
import com.example.inflearnJWT.dto.TokenDto;
import com.example.inflearnJWT.jwt.MyJwtFilter;
import com.example.inflearnJWT.jwt.MyJwtProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyAuthController {
	private final MyJwtProvider tokenProvider;
	private final AuthenticationManagerBuilder authenticationManagerBuilder; // 이게 어떻게 주입이 되는건가?

	//로그인 api
	@PostMapping("/authenticate")
	public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
		// 로그인을 할 객체 만들기
		UsernamePasswordAuthenticationToken authenticationToken 
			= new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
		
		// 로그인 성공 및 로그인 정보 세션에 저장
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		// 토큰 생성
		String jwt = tokenProvider.createToken(authentication);
		
		//Response에 넣을 헤더 설정
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(MyJwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
		
		//토큰정보, 헤더, 상태를 반환
		return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
	}
}
