package com.example.demo.toyprj;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/toyPrj")
public class ToyPrjController {
	
	@RequestMapping("/login")
	public String loginPage() {
		return "toyPrj/login";
	}
	
	@RequestMapping("/loginSuccess")
	public String loginSuccese(HttpServletRequest request, UserInfo user) {
		String userId = user.getId().get();
		String userPwd = user.getPassword().get();
		
		if(!user.getIdRemember().isPresent()) {
			System.out.println("비어있다.");
			user.setIdRemember(false);
		}
		
		boolean userRemember = user.getIdRemember().get();
		
		System.out.println("Id:"+userId);
		System.out.println("PW:"+userPwd);
		System.out.println(":"+userRemember);
		
		//DB 연결없이 하드코딩
		if(userId.equals("jang") && userPwd.equals("1234")) {
			return "toyPrj/loginSuccess";
		}
		
		System.out.println("아이디, 비밀번호가 틀림");
		return "redirect:login";
	}
	
}