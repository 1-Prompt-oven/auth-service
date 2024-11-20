package com.promptoven.authservice.domain;

import com.promptoven.authservice.domain.dto.OauthInfoModelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OauthInfo {

	private String provider;
	private String providerID;
	private String memberUUID;

	public static OauthInfo createOauthInfo(OauthInfoModelDTO oauthInfoModelDTO) {
		return OauthInfo.builder()
			.provider(oauthInfoModelDTO.getProvider())
			.providerID(oauthInfoModelDTO.getProviderID())
			.memberUUID(oauthInfoModelDTO.getMemberUUID())
			.build();
	}
}
