package com.mysite.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole {
	//관리자(ROLE_ADMIN) 또는 유저(ROLE_USER)
	ADMIN("ROLE_NAME"),
	USER("ROLE_USER");
	
	private String value;
	
	private UserRole(String value) {
		this.value = value;
	}
}
