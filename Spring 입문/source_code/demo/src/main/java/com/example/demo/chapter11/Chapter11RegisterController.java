package com.example.demo.chapter11;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class Chapter11RegisterController {
	
//	@RequestMapping("/register/step1")
	@RequestMapping("/step1")
	public String handleStep1(HttpServletRequest request) {
		String name = request.getParameter("name");
		System.out.println(name);
		return "register/step1";
	}
	
	@GetMapping("/step2")
	public String handleStep2Get() {
		return "redirect:/register/step1";
	}
	
	@PostMapping("/step2")
	public String handleStep2() {
		//...
		return "register/step2";
	}
	
	@GetMapping("/step3")
	public String handleStep3(@RequestParam(value="name", defaultValue = "jang")String userName) {
		System.out.println(userName);
		return "register/step3";
	}
	
	@RequestMapping("/dogPage")
	public String hadleDogPage(BarkDog dog) {
		if(dog != null) {
			System.out.println(dog.getBarkSound().get(0));
			System.out.println(dog.getBarkSound().get(1));
			System.out.println("이름: " + dog.getDog().getName());
			System.out.println("나이: " + dog.getDog().getAge());
		}
		return "register/dog";
	}
	
	@RequestMapping("/")
	public String firstModel(Model model) {
		ModelAndView mav = new ModelAndView();
		String name = "jang";
		model.addAttribute("name", name);
		return "index";
	}
}
