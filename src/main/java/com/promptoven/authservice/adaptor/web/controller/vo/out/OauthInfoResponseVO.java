package com.promptoven.authservice.adaptor.web.controller.vo.out;

import com.promptoven.authservice.application.service.dto.OauthInfoDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OauthInfoResponseVO {

	private String provider;
	private String providerID;

	public static OauthInfoResponseVO fromDTO(OauthInfoDTO oauthInfoDTO) {
		return OauthInfoResponseVO.builder()
			.provider(oauthInfoDTO.getProvider())
			.providerID(oauthInfoDTO.getProviderID())
			.build();
	}
}
