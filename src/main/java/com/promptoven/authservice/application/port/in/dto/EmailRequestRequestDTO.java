package com.promptoven.authservice.application.port.in.dto;

import lombok.Getter;

@Getter
public class EmailRequestRequestDTO {

	private final String email;

	public EmailRequestRequestDTO(String email) {
		this.email = email;
	}

}
