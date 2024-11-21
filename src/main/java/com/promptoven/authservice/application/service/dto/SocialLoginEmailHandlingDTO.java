package com.promptoven.authservice.application.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SocialLoginEmailHandlingDTO {

	private final String email;
	private final String provider;
	private final String providerId;
}
