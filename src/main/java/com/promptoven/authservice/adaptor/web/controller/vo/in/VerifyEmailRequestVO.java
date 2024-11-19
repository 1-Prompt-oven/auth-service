package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.VerifyEmailRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyEmailRequestVO {

	private String email;

	public VerifyEmailRequestDTO toDTO() {
		return VerifyEmailRequestDTO.builder()
			.email(email)
			.build();
	}
}
