package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.service.dto.LoginRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestVO {

	private String email;
	private String password;

	public LoginRequestDTO toDTO() {
		return LoginRequestDTO.builder().email(email).password(password).build();
	}
}
