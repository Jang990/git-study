package com.example.demo.chapter06;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

//@Component
public class ChatClient implements InitializingBean, DisposableBean{
	private String user;
	
	public ChatClient() {
		user = "jang"; 
		System.out.println("생성자");
	}
	
	public String getUser() {
		return user;
	}



	@Override
	public void destroy() throws Exception {
		System.out.println("객체 소멸");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println("객체 생성");
	}
	
}
