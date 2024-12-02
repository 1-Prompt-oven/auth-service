package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.RegisterSocialRequestVO;
import com.promptoven.authservice.application.port.in.dto.RegisterSocialRequestDTO;

public class RegisterSocialRequestMapper {

	public static RegisterSocialRequestDTO toDTO(RegisterSocialRequestVO vo) {
		return RegisterSocialRequestDTO.builder()
			.email(vo.getEmail())
			.password(vo.getPassword())
			.nickname(vo.getNickname())
			.provider(vo.getProvider())
			.providerId(vo.getProviderId())
			.build();
	}

} 