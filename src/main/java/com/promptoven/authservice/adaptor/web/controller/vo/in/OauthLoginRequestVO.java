package com.promptoven.authservice.adaptor.web.controller.vo.in;

import org.springframework.lang.Nullable;

import com.promptoven.authservice.application.port.in.dto.OauthLoginRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OauthLoginRequestVO {
	private String provider;
	private String providerID;
	private @Nullable String email;

	public OauthLoginRequestDTO toDTO() {
		return OauthLoginRequestDTO.builder()
			.provider(provider)
			.providerId(providerID)
			.email(email)
			.build();
	}
}
