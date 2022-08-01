package com.example.demo.chapter05;

import org.springframework.context.ApplicationContext; 
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Chapter5_Main {
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Chapter5Conf.class);
//		Person jang = ctx.getBean("jang", Person.class);
//		
//		System.out.println("jang의 펫: " + jang.getPet().getName());
		MyDog m = (MyDog) ctx.getBean("myDog");
		System.out.println("jang의 펫: " + m.getType());
	}
}
