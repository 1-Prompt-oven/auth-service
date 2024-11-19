package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.EmailRequestRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestRequestVO {

	private String email;

	public EmailRequestRequestDTO toDTO() {
		return new EmailRequestRequestDTO(email);
	}
}
