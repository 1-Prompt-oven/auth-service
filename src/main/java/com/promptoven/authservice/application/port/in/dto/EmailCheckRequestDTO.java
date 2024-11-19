package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class EmailCheckRequestDTO {

	private final String email;
	private final String code;

	@Builder
	public EmailCheckRequestDTO(String email, String code) {
		this.email = email;
		this.code = code;
	}
}
