package com.promptoven.authservice.adaptor.web.controller.vo.in;

import org.springframework.lang.Nullable;

import com.promptoven.authservice.application.port.in.dto.SocialLoginRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginRequestVO {
	private String provider;
	private String providerID;
	private @Nullable String email;

	public SocialLoginRequestDTO toDTO() {
		return SocialLoginRequestDTO.builder()
			.provider(provider)
			.providerId(providerID)
			.email(email)
			.build();
	}
}
