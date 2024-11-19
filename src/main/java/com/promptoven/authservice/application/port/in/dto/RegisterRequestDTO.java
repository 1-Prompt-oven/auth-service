package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterRequestDTO {

	private final String email;
	private final String password;
	private final String nickname;

	@Builder
	public RegisterRequestDTO(String email, String password, String nickname) {
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}
}
