package com.example.demo.chapter02;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.chapter02.Person;

@Configuration
public class Chapter2Conf {
	
	@Bean
	public Person person() {
		Person p = new Person();
		p.setAge(24);
		p.setName("Jang");
		return p;
	}
	
}
