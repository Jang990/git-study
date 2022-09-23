package com.example.inflearnJWT.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inflearnJWT.dto.UserDto;
import com.example.inflearnJWT.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyUserController {
	private final UserService userService;

	@GetMapping("/hello")
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("hello");
	}

	@PostMapping("/test-redirect")
	public void testRedirect(HttpServletResponse response) throws IOException {
		response.sendRedirect("/api/user");
	}
	
	//회원가입
	//시큐리티 설정에서 .antMatchers("/api/signup").permitAll() 다음과 같이 설저했기 때문에 접근하는데 문제가 없다.
	@PostMapping("/signup")
	public ResponseEntity<UserDto> signup(@Valid @RequestBody UserDto userDto) {
		return ResponseEntity.ok(userService.signup(userDto));
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public ResponseEntity<UserDto> getMyUserInfo(HttpServletRequest request) {
		return ResponseEntity.ok(userService.getMyUserWithAuthorities());
	}

	@GetMapping("/user/{username}")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<UserDto> getUserInfo(@PathVariable String username) {
		return ResponseEntity.ok(userService.getUserWithAuthorities(username));
	}
}
