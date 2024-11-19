package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.RegisterRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestVO {

	private String email;
	private String password;
	private String nickname;

	public RegisterRequestDTO toDTO() {
		return RegisterRequestDTO.builder().email(email).password(password).nickname(nickname).build();
	}

}
