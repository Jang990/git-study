package com.example.demo.chapter13;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/chapter13")
public class Chap13Controller {

	@RequestMapping("/hello")
	public String hello(Model model,
			@RequestParam(value="name", required = false)String name) {
		model.addAttribute("name", name);
		return "index";
	}
	
	@RequestMapping("/sessionCheck1")
	public String sessionEx2(HttpSession session) {
		return "index";
	}
	
	@RequestMapping("/sessionCheck2")
	public String sessionEx1(AuthInfo authInfo, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.setAttribute("authInfo", authInfo);
		return "index";
	}
	
	@RequestMapping("/cookieCheck2")
	public String cookieEx(@CookieValue(value="REMEMBER", required = false)Cookie rCookie) {
		if(rCookie != null) {
			
		}
		return "index";
	}
}
