package com.example.demo.chapter05;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("jang")
public class Person {
	@Autowired
	private Dog pet;

	public Dog getPet() {
		return pet;
	}
}
