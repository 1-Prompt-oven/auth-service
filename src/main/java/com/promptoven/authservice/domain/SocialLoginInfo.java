package com.promptoven.authservice.domain;

import com.promptoven.authservice.domain.dto.SocialLoginInfoModelDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SocialLoginInfo {

	private String provider;
	private String providerID;
	private String memberUUID;

	public static SocialLoginInfo createSocialLoginInfo(SocialLoginInfoModelDTO socialLoginInfoModelDTO) {
		return SocialLoginInfo.builder()
			.provider(socialLoginInfoModelDTO.getProvider())
			.providerID(socialLoginInfoModelDTO.getProviderID())
			.memberUUID(socialLoginInfoModelDTO.getMemberUUID())
			.build();
	}
}
