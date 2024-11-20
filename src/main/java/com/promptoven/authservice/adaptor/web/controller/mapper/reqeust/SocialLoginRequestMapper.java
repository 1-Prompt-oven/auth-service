package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.SocialLoginRequestVO;
import com.promptoven.authservice.application.port.in.dto.SocialLoginRequestDTO;

public class SocialLoginRequestMapper {

	public static SocialLoginRequestDTO toDTO(SocialLoginRequestVO vo) {
		return SocialLoginRequestDTO.builder()
			.provider(vo.getProvider())
			.providerId(vo.getProviderID())
			.email(vo.getEmail())
			.build();
	}
} 