package com.promptoven.authservice.adaptor.web.controller.mapper.response;

import com.promptoven.authservice.adaptor.web.controller.vo.out.SocialLoginInfoResponseVO;
import com.promptoven.authservice.application.service.dto.SocialLoginInfoDTO;

public class OauthInfoResponseMapper {

	public static SocialLoginInfoResponseVO fromDTO(SocialLoginInfoDTO dto) {
		return SocialLoginInfoResponseVO.builder()
			.provider(dto.getProvider())
			.providerID(dto.getProviderID())
			.build();
	}
}
