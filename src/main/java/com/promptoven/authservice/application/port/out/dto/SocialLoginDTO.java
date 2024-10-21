package com.promptoven.authservice.application.port.out.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SocialLoginDTO {

	private final String accessToken;
	private final String refreshToken;
	private final String role;
	private final String uuid;
	private final String nickname;
	private final boolean failed;

	@Builder
	public SocialLoginDTO(String accessToken, String refreshToken, String role, String uuid, String nickname) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.role = role;
		this.uuid = uuid;
		this.nickname = nickname;
		this.failed = false;
	}

	public SocialLoginDTO() {
		this.accessToken = null;
		this.refreshToken = null;
		this.role = null;
		this.uuid = null;
		this.nickname = null;
		this.failed = true;
	}
}
