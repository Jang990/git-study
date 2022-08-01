package com.example.demo.chapter05;

import org.springframework.stereotype.Component;

@Component
public class MyDog {
	private String type;
	
	public MyDog() {
		this.type = "자동";
	}
	
	public MyDog(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
