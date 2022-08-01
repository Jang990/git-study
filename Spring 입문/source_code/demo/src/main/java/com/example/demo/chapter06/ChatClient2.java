package com.example.demo.chapter06;

public class ChatClient2 {
	private String user;
	
	public ChatClient2() {
		user = "jang"; 
		System.out.println("생성자");
	}
	
	public String getUser() {
		return user;
	}

	public void init() {
		System.out.println("객체 생성");
	}

	public void close() {
		System.out.println("객체 소멸");
	}
}
