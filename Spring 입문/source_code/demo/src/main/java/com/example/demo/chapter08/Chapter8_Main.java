package com.example.demo.chapter08;

import org.springframework.context.ApplicationContext; 
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

public class Chapter8_Main {
	public static void main(String[] args) {
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(Chapter8Conf.class);
//		ChatClient c = ctx.getBean(ChatClient.class);
//		System.out.println(c.getUser());
//		ctx.close();

	}
}
