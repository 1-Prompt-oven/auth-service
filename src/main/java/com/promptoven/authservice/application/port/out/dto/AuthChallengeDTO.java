package com.promptoven.authservice.application.port.out.dto;

import java.util.Date;

import lombok.Builder;

public class AuthChallengeDTO {

	private final String code;
	private final String media;
	private final Date expires;

	@Builder
	public AuthChallengeDTO(String code, String media, Date expires) {
		this.code = code;
		this.media = media;
		this.expires = expires;
	}
}
