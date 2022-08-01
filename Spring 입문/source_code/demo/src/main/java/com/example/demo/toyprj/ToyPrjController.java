package com.example.demo.toyprj;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/toyPrj")
public class ToyPrjController {
	
	//chapter13/login
	@RequestMapping("/login")
	public String loginPage() {
		return "chapter13/login";
	}
	
	@RequestMapping("/loginSuccess")
	public String loginSuccese(HttpServletRequest request, UserInfo user) {
		//DB 연결없이 하드코딩
		if(user.getId().isEmpty() || user.getPassword().isEmpty()) {
			System.out.println("아이디와 비밀번호를 전부 작성할 것");
			return "sendRediect:/chapter13/login";
		}
		
		String userId = user.getId().get();
		String userPwd = user.getPassword().get();
		
		if(userId.equals("jang") && userPwd.equals("1234")) {
			return "chapter13/loginSuccess";
		}
		
		System.out.println("아이디, 비밀번호가 틀림");
		return "sendRediect:/chapter13/login";
	}
	
}