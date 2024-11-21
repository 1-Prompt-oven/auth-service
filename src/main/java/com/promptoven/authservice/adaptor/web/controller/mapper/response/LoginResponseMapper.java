package com.promptoven.authservice.adaptor.web.controller.mapper.response;

import com.promptoven.authservice.adaptor.web.controller.vo.out.LoginResponseVO;
import com.promptoven.authservice.application.port.out.dto.LoginResponseDTO;

public class LoginResponseMapper {

	public static LoginResponseVO fromDTO(LoginResponseDTO dto) {
		return new LoginResponseVO(dto.getAccessToken(),
			dto.getRefreshToken(),
			dto.getNickname(),
			dto.getRole(),
			dto.getUuid());
	}
}
