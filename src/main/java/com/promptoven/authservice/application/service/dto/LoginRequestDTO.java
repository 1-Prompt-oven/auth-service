package com.promptoven.authservice.application.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequestDTO {

	private final String email;
	private final String password;

	@Builder
	public LoginRequestDTO(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
