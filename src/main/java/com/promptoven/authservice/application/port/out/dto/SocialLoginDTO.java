package com.promptoven.authservice.application.port.out.dto;

import lombok.Getter;

@Getter
public class SocialLoginDTO {

	private final String accessToken;
	private final String refreshToken;
	private final boolean failed;

	public SocialLoginDTO(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.failed = false;
	}

	public SocialLoginDTO() {
		this.accessToken = null;
		this.refreshToken = null;
		this.failed = true;
	}
}
