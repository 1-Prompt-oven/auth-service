package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UnbanRequestDTO implements MemberUUIDOnlyDTO {
	private final String memberUUID;

	@Builder
	public UnbanRequestDTO(String memberUUID) {
		this.memberUUID = memberUUID;
	}
}
