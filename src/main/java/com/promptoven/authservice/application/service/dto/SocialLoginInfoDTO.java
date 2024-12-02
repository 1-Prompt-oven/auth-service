package com.promptoven.authservice.application.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SocialLoginInfoDTO {

	private final String provider;
	private final String providerID;
	private final String memberUUID;

	@Builder
	public SocialLoginInfoDTO(String provider, String providerID, String memberUUID) {
		this.provider = provider;
		this.providerID = providerID;
		this.memberUUID = memberUUID;
	}

}
