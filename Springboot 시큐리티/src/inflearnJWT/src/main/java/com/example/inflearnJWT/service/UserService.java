package com.example.inflearnJWT.service;

import java.util.Collections;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.inflearnJWT.dto.UserDto;
import com.example.inflearnJWT.entity.Authority;
import com.example.inflearnJWT.entity.User;
import com.example.inflearnJWT.exception.DuplicateMemberException;
import com.example.inflearnJWT.exception.NotFoundMemberException;
import com.example.inflearnJWT.repository.UserRepository;
import com.example.inflearnJWT.util.SecurityUtil;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //회원가입 로직
    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }
        
        //권한정보 생성
        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();
        
        //유저를 생성하면서 생성한 권한정보까지 넣음
        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .nickname(userDto.getNickname())
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();

        return UserDto.from(userRepository.save(user));
    }
    
    
    
    /*
     * 즉 아래에 2개의 메서드 중
     * getMyUserWithAuthorities()는 지금 사용자의 정보를 가져오기 때문에 위험이 없지만 
     * getUserWithAuthorities(String username)는 지금 사용자가 아닌 다른 사용자의 정보를 가져오기 때문에 
     * ADMIN 권한이 아닌 사용자가 사용하면 위험하다.
     */
    
    // 인자로 받은 username을 기준으로 정보를 DB에서 가져옴
    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }
    
    // SecurityContext에 저장된 username의 정보를 DB에서 가져옴
    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }
}