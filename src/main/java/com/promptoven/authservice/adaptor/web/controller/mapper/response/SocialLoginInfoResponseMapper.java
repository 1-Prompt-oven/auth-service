package com.promptoven.authservice.adaptor.web.controller.mapper.response;

import com.promptoven.authservice.adaptor.web.controller.vo.out.SocialLoginInfoResponseVO;
import com.promptoven.authservice.application.service.dto.SocialLoginInfoDTO;

public class SocialLoginInfoResponseMapper {

	public static SocialLoginInfoResponseVO fromDTO(SocialLoginInfoDTO dto) {
		return SocialLoginInfoResponseVO.builder()
			.providerID(dto.getProviderID())
			.provider(dto.getProvider())
			.build();
	}
}
