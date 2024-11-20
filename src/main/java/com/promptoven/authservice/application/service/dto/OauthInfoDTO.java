package com.promptoven.authservice.application.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthInfoDTO {

	private final String provider;
	private final String providerID;
	private final String memberUUID;

	@Builder
	public OauthInfoDTO(String provider, String providerID, String memberUUID) {
		this.provider = provider;
		this.providerID = providerID;
		this.memberUUID = memberUUID;
	}

}
