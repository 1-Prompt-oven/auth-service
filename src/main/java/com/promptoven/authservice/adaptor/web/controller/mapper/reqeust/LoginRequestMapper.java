package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.LoginRequestVO;
import com.promptoven.authservice.application.service.dto.LoginRequestDTO;

public class LoginRequestMapper {

	public static LoginRequestDTO toDTO(LoginRequestVO vo) {
		return LoginRequestDTO.builder()
			.email(vo.getEmail())
			.password(vo.getPassword())
			.build();
	}

} 