package com.example.demo.toyprj;

import java.util.Optional;

public class UserInfo {
	private Optional<String> id;
	private Optional<String> password;
	private Optional<Boolean> idRemember;
	
	public UserInfo() {
		this.id = Optional.empty();
		this.password = Optional.empty();
		this.idRemember = Optional.empty();
	}
	
	public Optional<String> getId() {
		return id;
	}
	public void setId(String id) {
		System.out.println(id);
		this.id = Optional.ofNullable(id);
	}
	public Optional<String> getPassword() {
		return password;
	}
	public void setPassword(String password) {
		System.out.println(password);
		this.password = Optional.ofNullable(password);
	}
	public Optional<Boolean> getIdRemember() {
		return idRemember;
	}
	public void setIdRemember(Boolean idRemember) {
		System.out.println(idRemember);
		this.idRemember = Optional.ofNullable(idRemember);
	}
	
	
}
