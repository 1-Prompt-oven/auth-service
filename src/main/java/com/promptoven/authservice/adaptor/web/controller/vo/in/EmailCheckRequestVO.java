package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.EmailCheckRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailCheckRequestVO {

	private String email;
	private String code;

	public EmailCheckRequestDTO toDTO() {
		return EmailCheckRequestDTO.builder().email(email).code(code).build();
	}
}
