package com.promptoven.authservice.application.port.out.dto;

import lombok.Getter;

@Getter
public class LoginDTO {

	private final String accessToken;
	private final String refreshToken;

	public LoginDTO(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
