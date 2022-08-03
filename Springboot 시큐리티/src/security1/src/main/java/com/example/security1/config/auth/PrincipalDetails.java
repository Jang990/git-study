package com.example.security1.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.security1.model.User;

public class PrincipalDetails implements UserDetails {
	
	User user; //컴포지션
	public PrincipalDetails(User user) {
		this.user = user;
	}
	
	//해당 User의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//우리는 user.getRole()로 권한을 리턴하면 되지만 정한 리턴타입이 있으니까 코드로 만들어준다.
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(new GrantedAuthority() {
			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return user.getRole();
			}
		});
		
		return collect;
	}

	//패스워드 리턴
	@Override
	public String getPassword() {
		return user.getPassword();
	}
	
	//이름 리턴
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	//계정 만료 여부
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	//계정 잠김 여부
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	//계정 비밀번호의 기간이 지났니?(너무 오래 사용한거 아니니?)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	//계정이 활성화 되어있니?
	@Override
	public boolean isEnabled() {
		//우리 사이트에서 1년간 접속을 하지않으면 휴면계정으로 처리하도록 했다면 
		//현재 시간 - 최근 로그인시간 > 1년 이라면 return false; 이렇게 구현하면 된다.
		return true; 
	}
	
	//아니오는 전부 true이다.

}
