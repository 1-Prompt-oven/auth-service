package com.promptoven.authservice.application.port.out.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class LoginResponseDTO {

	private final String accessToken;
	private final String refreshToken;
	private final String role;
	private final String uuid;
	private final String nickname;

}
