package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.SocialLoginAssociateRequestVO;
import com.promptoven.authservice.application.port.in.dto.SocialLoginAssociateRequestDTO;

public class SocialLoginAssociateRequestMapper {

	public static SocialLoginAssociateRequestDTO toDTO(SocialLoginAssociateRequestVO vo) {
		return SocialLoginAssociateRequestDTO.builder()
			.provider(vo.getProvider())
			.providerId(vo.getProviderId())
			.memberUUID(vo.getMemberUUID())
			.build();
	}

} 