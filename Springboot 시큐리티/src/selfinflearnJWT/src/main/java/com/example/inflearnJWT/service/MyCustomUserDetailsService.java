package com.example.inflearnJWT.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.inflearnJWT.entity.User;
import com.example.inflearnJWT.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MyCustomUserDetailsService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findOneWithAuthoritiesByUsername(username)
				.map(user -> craeteUser(username, user)) // 시큐리티의 User 객체 생성
				.orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
	}

	// 이 부분은 강사의 스타일인 것 같다.
	// 예전에 메타코딩 시큐리티 1~5강에 내용에서 한 것처럼 UserDetails 인터페이스를 구현하는 클래스를 만들어서 그걸로 적용해도 될 것 같다.
	// 지금의 강사님은 기본 시큐리티 User 클래스를 사용한다.
	private UserDetails craeteUser(String username, User user) {
		if (!user.isActivated()) {
			throw new RuntimeException(username + " -> 활성화되어 있지 않습니다.");
		}
		
		//권한 뽑아내기
		List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
				.map(authority -> new SimpleGrantedAuthority(authority.getAuthorityName()))
				.collect(Collectors.toList());
		
		// 시큐리티 User 객체 생성 후 반환
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}

}
