package com.example.demo.chapter02;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.demo.chapter02.Person;

public class Chapter2_Main {
	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(Chapter2Conf.class);
		Person jang = ctx.getBean("person", Person.class);
		System.out.println("이름 : " + jang.getName());
		System.out.println("나이 : " + jang.getAge());
		
		Person jang1 = ctx.getBean("person", Person.class);
		System.out.println("이름 : " + jang.getName());
		System.out.println("나이 : " + jang.getAge());
		
		System.out.println("싱글톤 체크: " + (jang == jang1));
	}
}
