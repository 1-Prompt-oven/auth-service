package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class RegisterSocialRequestDTO {
    private final String email;
    private final String password;
    private final String nickname;
    private final String provider;
    private final String providerId;

	public RegisterRequestDTO toRegisterRequestDTO() {
		return RegisterRequestDTO.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.build();
	}
} 