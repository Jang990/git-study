package com.example.selfPrj.config.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.selfPrj.entity.MemberInfo;
import com.example.selfPrj.repository.MemberInfoRepository;

@Service
public class AuthUserDetailService implements UserDetailsService{

	@Autowired
	MemberInfoRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<MemberInfo> memberInfo = memberRepository.findByUsername(username);
		if(memberInfo.isPresent()) {
			return new MemberUserDetails(memberInfo.get());
		}
		return null;
	}

}
