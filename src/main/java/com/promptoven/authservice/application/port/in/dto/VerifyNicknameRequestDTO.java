package com.promptoven.authservice.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;

@AllArgsConstructor
@Getter
@Builder
public class VerifyNicknameRequestDTO {
	private final String nickname;
}