package com.example.security1.config.auth.simpleway;

import java.util.Collection;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SecurityUser extends User{
	private com.example.security1.model.User user;
	
	public SecurityUser(com.example.security1.model.User user) {
		super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole()));
		this.user = user;
	}
	
}
