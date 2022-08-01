package com.example.demo.chapter06;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = {"com.example.demo.chapter6"})
public class Chapter6Conf {
	@Bean(initMethod = "init", destroyMethod = "close")
	public ChatClient2 chatClient2() {
		ChatClient2 c = new ChatClient2();
		return c;
	}
}
