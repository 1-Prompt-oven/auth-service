package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.SocialLoginDisassociateRequestVO;
import com.promptoven.authservice.application.port.in.dto.SocialLoginDisassociateRequestDTO;

public class SocialLoginDisassociateRequestMapper {

	public static SocialLoginDisassociateRequestDTO toDTO(SocialLoginDisassociateRequestVO vo) {
		return SocialLoginDisassociateRequestDTO.builder()
			.provider(vo.getProvider())
			.providerId(vo.getProviderId())
			.memberUUID(vo.getMemberUUID())
			.build();
	}

} 