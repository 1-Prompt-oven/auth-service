package com.promptoven.authservice.adaptor.web.controller.vo.out;

import com.promptoven.authservice.application.port.out.dto.LoginDTO;

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

	public static LoginResponseVO from(LoginDTO loginDTO) {
		return new LoginResponseVO(loginDTO.getAccessToken(), loginDTO.getRefreshToken(), loginDTO.getNickname(),
			loginDTO.getRole(), loginDTO.getUuid());
	}
}
