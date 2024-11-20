package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.RegisterRequestVO;
import com.promptoven.authservice.application.port.in.dto.RegisterRequestDTO;

public class RegisterRequestMapper {

	public static RegisterRequestDTO toDTO(RegisterRequestVO vo) {
		return RegisterRequestDTO.builder()
			.email(vo.getEmail())
			.password(vo.getPassword())
			.nickname(vo.getNickname())
			.build();
	}

}