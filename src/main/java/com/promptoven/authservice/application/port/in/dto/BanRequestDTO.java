package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BanRequestDTO implements MemberUUIDOnlyDTO {

	private final String memberUUID;

	@Override
	public String memberUUID() {
		return memberUUID;
	}
}