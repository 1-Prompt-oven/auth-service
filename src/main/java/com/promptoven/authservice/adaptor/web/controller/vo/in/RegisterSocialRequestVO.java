package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.RegisterSocialRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterSocialRequestVO {

	private String email;
	private String password;
	private String nickname;
	private String provider;
	private String providerId;

	public RegisterSocialRequestDTO toDTO() {
		return RegisterSocialRequestDTO.builder()
			.email(email)
			.password(password)
			.nickname(nickname)
			.provider(provider)
			.providerId(providerId)
			.build();
	}

}
