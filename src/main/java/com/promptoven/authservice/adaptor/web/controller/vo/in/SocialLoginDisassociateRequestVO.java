package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.SocialLoginDisassociateRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginDisassociateRequestVO {

	private String provider;
	private String providerId;
	private String memberUUID;

	public SocialLoginDisassociateRequestDTO toDTO() {
		return SocialLoginDisassociateRequestDTO.builder()
			.provider(provider)
			.providerId(providerId)
			.memberUUID(memberUUID)
			.build();
	}

}
