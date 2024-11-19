package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PromoteToSellerRequestDTO implements MemberUUIDOnlyDTO {

	private final String memberUUID;

	@Builder
	public PromoteToSellerRequestDTO(String memberUUID) {
		this.memberUUID = memberUUID;
	}
}
