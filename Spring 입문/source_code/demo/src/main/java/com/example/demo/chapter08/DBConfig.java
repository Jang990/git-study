package com.example.demo.chapter08;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class DBConfig {
	
	@Bean(destroyMethod = "close")
	public HikariDataSource dataSource() {
		HikariDataSource ds = new HikariDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setJdbcUrl("jdbc:mysql//localhost:3306/testTable");
		ds.setUsername("test");
		ds.setPassword("test");
		ds.setValidationTimeout(10000);
		ds.setConnectionTimeout(10000);
		return ds;
	}
	
}
