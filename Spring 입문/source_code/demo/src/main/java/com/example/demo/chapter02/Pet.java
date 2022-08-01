package com.example.demo.chapter02;

public class Pet {
	
	public Pet() {
		this.name = "강아지";
	}
	
	public Pet(String name) {
		this.name = name;
	}
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
