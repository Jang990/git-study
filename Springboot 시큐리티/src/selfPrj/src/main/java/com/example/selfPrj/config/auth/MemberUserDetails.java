package com.example.selfPrj.config.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.selfPrj.entity.MemberInfo;

public class MemberUserDetails extends User {

	private MemberInfo memberInfo;
	
	//String username, String password, Collection<? extends GrantedAuthority> authorities
	public MemberUserDetails(MemberInfo memberInfo) {
		super(memberInfo.getUsername(), memberInfo.getPassword(), AuthorityUtils.createAuthorityList(memberInfo.getRole()));
		this.memberInfo = memberInfo;
	}
	
}
