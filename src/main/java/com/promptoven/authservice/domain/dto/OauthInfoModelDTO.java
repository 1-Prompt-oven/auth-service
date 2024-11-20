package com.promptoven.authservice.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthInfoModelDTO {
	private final String provider;
	private final String providerID;
	private final String memberUUID;

	@Builder
	public OauthInfoModelDTO(String provider, String providerID, String memberUUID) {
		this.provider = provider;
		this.providerID = providerID;
		this.memberUUID = memberUUID;
	}
}
