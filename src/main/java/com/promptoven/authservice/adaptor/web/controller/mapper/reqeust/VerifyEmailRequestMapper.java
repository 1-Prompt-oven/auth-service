package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.VerifyEmailRequestVO;
import com.promptoven.authservice.application.port.in.dto.VerifyEmailRequestDTO;

public class VerifyEmailRequestMapper {

	public static VerifyEmailRequestDTO toDTO(VerifyEmailRequestVO vo) {
		return VerifyEmailRequestDTO.builder()
			.email(vo.getEmail())
			.build();
	}
}
