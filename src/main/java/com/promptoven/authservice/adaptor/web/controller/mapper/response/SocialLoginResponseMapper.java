package com.promptoven.authservice.adaptor.web.controller.mapper.response;

import com.promptoven.authservice.adaptor.web.controller.vo.out.SocialLoginResponseVO;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;

public class SocialLoginResponseMapper {

	public static SocialLoginResponseVO fromDTO(SocialLoginDTO dto) {
		return SocialLoginResponseVO.builder()
			.accesstoken(dto.getAccessToken())
			.refreshtoken(dto.getRefreshToken())
			.memberUUID(dto.getUuid())
			.nickname(dto.getNickname())
			.role(dto.getRole())
			.build();

	}
}
