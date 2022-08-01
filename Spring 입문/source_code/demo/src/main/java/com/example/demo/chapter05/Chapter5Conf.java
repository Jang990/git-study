package com.example.demo.chapter05;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = {"com.example.demo.chapter5"})
public class Chapter5Conf {
//	@Bean
//	public Pet pet1() {
//		return new Pet();
//	}
//	
//	@Bean
//	public Person person() {
//		return new Person();
//	}
	
//	@Bean //웹 실행시키려했는데 myDog 빈이 두개라 오류나서 잠시 막아놓음 주석해제하고 챕터5 메인실행시키면 문제는 없음
	public MyDog myDog() {
		return new MyDog("수동");
	}
}
