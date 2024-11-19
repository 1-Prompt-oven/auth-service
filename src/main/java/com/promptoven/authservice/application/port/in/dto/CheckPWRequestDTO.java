package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CheckPWRequestDTO {

	private final String password;
	private final String memberUUID;

	@Builder
	public CheckPWRequestDTO(String password, String memberUUID) {
		this.password = password;
		this.memberUUID = memberUUID;
	}

}
