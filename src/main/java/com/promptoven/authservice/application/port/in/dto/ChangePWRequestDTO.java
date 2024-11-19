package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ChangePWRequestDTO {

	private final String newPassword;
	private final String memberUUID;

	@Builder
	public ChangePWRequestDTO(String newPassword, String memberUUID) {
		this.newPassword = newPassword;
		this.memberUUID = memberUUID;
	}

}
