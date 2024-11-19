package com.promptoven.authservice.adaptor.web.controller.vo.out;

import com.promptoven.authservice.application.port.out.dto.LoginResponseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class LoginResponseVO {

	private String accesstoken;
	private String refreshtoken;
	private String nickname;
	private String role;
	private String memberUUID;

	public static LoginResponseVO from(LoginResponseDTO loginResponseDTO) {
		return new LoginResponseVO(loginResponseDTO.getAccessToken(), loginResponseDTO.getRefreshToken(),
			loginResponseDTO.getNickname(),
			loginResponseDTO.getRole(), loginResponseDTO.getUuid());
	}
}
