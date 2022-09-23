package com.example.inflearnJWT.util;

import java.util.Optional; 

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class MySecurityUtil {

	private static final Logger logger = LoggerFactory.getLogger(MySecurityUtil.class);

	private MySecurityUtil() {
	}

	public static Optional<String> getCurrentUsername() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication == null) {
			logger.debug("Security Context에 인증 정보가 없습니다.");
			return Optional.empty();
		}
		
		String username = null;
		if (authentication.getPrincipal() instanceof UserDetails) {
			UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
			username = springSecurityUser.getUsername();
		} else if (authentication.getPrincipal() instanceof String) {
			//어떨때 이부분을 타게되는 건지 모르겠다. authentication.getPrincipal()에서 String을 받는다니?
			username = (String) authentication.getPrincipal();
		}

		return Optional.ofNullable(username);
	}
}
