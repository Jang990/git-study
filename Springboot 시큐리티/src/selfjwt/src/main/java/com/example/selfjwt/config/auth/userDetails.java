package com.example.selfjwt.config.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.selfjwt.model.UserModel;

import lombok.Data;

@Data
public class userDetails extends User{
	private UserModel user;
	
	public userDetails(UserModel user) {
		super(user.getUsername(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRoles()));
		this.user = user;
	}

}
