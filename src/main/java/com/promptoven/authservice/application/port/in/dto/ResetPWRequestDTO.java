package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResetPWRequestDTO {

	private final String email;
	private final String password;

	@Builder
	public ResetPWRequestDTO(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
