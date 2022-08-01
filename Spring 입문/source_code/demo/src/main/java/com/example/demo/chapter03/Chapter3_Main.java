package com.example.demo.chapter03;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.demo.chapter02.Person;

public class Chapter3_Main {
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Chapter3Conf.class);
		Person jang = ctx.getBean("person", Person.class);
		Person jang1 = ctx.getBean("person", Person.class);
		
		System.out.println("jang의 펫: " + jang.getPet().getName());
		System.out.println("jang1의 펫: " + jang1.getPet().getName());
		
		System.out.println("싱글톤 체크 : " + (jang.getPet() == jang1.getPet()));		
		
	}
}
