package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.SocialLoginAssociateRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginAssociateRequestVO {

	private String provider;
	private String providerId;
	private String memberUUID;

	public SocialLoginAssociateRequestDTO toDTO() {
		return SocialLoginAssociateRequestDTO.builder()
			.provider(provider)
			.providerId(providerId)
			.memberUUID(memberUUID)
			.build();
	}

}
