package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.ResetPWRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResetPWRequestVO {

	private String email;
	private String password;

	public ResetPWRequestDTO toDTO() {
		return ResetPWRequestDTO.builder()
			.email(email)
			.password(password)
			.build();
	}

}
