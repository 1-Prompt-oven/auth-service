package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.OauthRegisterRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OauthRegisterRequestVO {

	private String provider;
	private String providerId;
	private String memberUUID;

	public OauthRegisterRequestDTO toDTO() {
		return OauthRegisterRequestDTO.builder()
			.provider(provider)
			.providerId(providerId)
			.memberUUID(memberUUID)
			.build();
	}

}
