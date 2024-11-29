package com.promptoven.authservice.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ChangePWRequestDTO {
	private final String newPassword;
	private final String memberUUID;

	public String memberUUID() {
		return memberUUID;
	}
}
