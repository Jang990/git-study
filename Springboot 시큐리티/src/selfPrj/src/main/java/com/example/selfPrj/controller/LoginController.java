package com.example.selfPrj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.selfPrj.entity.MemberInfo;
import com.example.selfPrj.repository.MemberInfoRepository;

@Controller
public class LoginController {
	
	@Autowired
	private MemberInfoRepository memberRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@RequestMapping({"", "/index"})
	public String index() {
		return "index";
	}
	
	@RequestMapping("/join")
	public String joinForm() {
		return "joinForm";
	}
	
	@RequestMapping("/user/index")
	public String userIndex() {
		return "user/index";
	}
	
	@RequestMapping("/join.do")
	public String join(MemberInfo member) {
		member.setRole("ROLE_USER");
		String rawPwd = member.getPassword();
		String encodedPwd = bCryptPasswordEncoder.encode(rawPwd);
		member.setPassword(encodedPwd);

		memberRepository.save(member);
		return "redirect:/loginForm";
	}
	
	@RequestMapping("/loginForm")
	public String loginForm() {
		return "loginForm";
	}
	
	@RequestMapping("/important")
	public String important() {
		return "admin/important";
	}
	
}
