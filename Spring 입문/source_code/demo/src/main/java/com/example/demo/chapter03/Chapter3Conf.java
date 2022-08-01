package com.example.demo.chapter03;

import org.springframework.beans.factory.annotation.Qualifier; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.chapter02.Person;
import com.example.demo.chapter02.Pet;

@Configuration
public class Chapter3Conf {
	@Bean
	@Qualifier("dog")
	public Pet pet1() {
		return new Pet();
	}
	
	@Bean
	public Pet pet2() {
		return new Pet("고양이");
	}
	
	@Bean
	public Person person1() {
		Person p = new Person();
		p.setAge(24);
		p.setName("Jang");
		p.setPet(pet2());
		return p;
	}
}
