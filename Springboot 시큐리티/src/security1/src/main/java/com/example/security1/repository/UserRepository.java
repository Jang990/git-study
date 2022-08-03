package com.example.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security1.model.User;

//기본 CRUD 함수를 JpaRepository가 들고 있다.
// @Repository라는 어노테이션 없이 IoC가 된다. 이유는 JpaRepository를 상속했기 때문이다.
public interface UserRepository extends JpaRepository<User, Integer>{
	public User findByUsername(String username);
}
