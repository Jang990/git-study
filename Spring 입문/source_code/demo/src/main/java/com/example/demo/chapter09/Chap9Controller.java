package com.example.demo.chapter09;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class Chap9Controller {

	@RequestMapping("/hello")
	public String hello(Model model,
			@RequestParam(value="name", required = false)String name) {
		model.addAttribute("name", name);
		return "index";
	}
}
