package com.example.demo.chapter02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Person {
	private String name;
	private int age;
	
	private Pet pet;

	@Autowired
	@Qualifier("dog")
	public void setPet(Pet pet) {
		this.pet = pet;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Pet getPet() {
		return pet;
	}
	
//	public void setPet(Pet pet) {
//		this.pet = pet;
//	}
	
}
