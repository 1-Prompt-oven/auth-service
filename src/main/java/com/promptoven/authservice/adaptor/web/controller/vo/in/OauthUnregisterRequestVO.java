package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.OauthUnregisterRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OauthUnregisterRequestVO {

	private String provider;
	private String providerId;
	private String memberUUID;

	public OauthUnregisterRequestDTO toDTO() {
		return OauthUnregisterRequestDTO.builder()
			.provider(provider)
			.providerId(providerId)
			.memberUUID(memberUUID)
			.build();
	}

}
