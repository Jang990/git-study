package com.example.demo.chapter05;

import org.springframework.stereotype.Component;

@Component
public class Dog {
	private String name;
	
	public Dog() {
		this.name = "강아지";
	}
	
	public Dog(String name) {
		this.name = name;
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
