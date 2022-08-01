package com.example.demo.chapter06;

import org.springframework.context.ApplicationContext; 
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Chapter6_Main {
	public static void main(String[] args) {
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(Chapter6Conf.class);
//		ChatClient c = ctx.getBean(ChatClient.class);
//		System.out.println(c.getUser());
//		ctx.close();
		
		ChatClient2 c = ctx.getBean(ChatClient2.class);
		System.out.println(c.getUser());
		ctx.close();
	}
}
