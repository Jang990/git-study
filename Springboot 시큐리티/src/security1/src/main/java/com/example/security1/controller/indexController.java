package com.example.security1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class indexController {

	//로그인한 사람만 접근
	@GetMapping({"", "/"})
	public String index() {
		return "index";
	}
	
	//로그인한 사람만 접근
	@GetMapping("/user")
	public @ResponseBody String user() {
		return "user";
	}
	
	//로그인한 사람중에 admin 권한이 있는 사람만
	@GetMapping("/admin")
	public @ResponseBody String admin() {
		return "admin";
	}
	
	//로그인한 사람중에 manager 권한이 있는 사람만 
	@GetMapping("/manager")
	public @ResponseBody String manager() {
		return "manager";
	}
	
	@GetMapping("/login")
	public @ResponseBody String login() {
		return "login";
	}
	
	@GetMapping("/join")
	public @ResponseBody String join() {
		return "join";
	}
	
	@GetMapping("/joinProc")
	public @ResponseBody String joinProc() {
		return "회원가입 완료됨";
	}
}
