package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.ResetPWRequestVO;
import com.promptoven.authservice.application.port.in.dto.ResetPWRequestDTO;

public class ResetPWRequestMapper {

	public static ResetPWRequestDTO toDTO(ResetPWRequestVO vo) {
		return ResetPWRequestDTO.builder()
			.email(vo.getEmail())
			.password(vo.getPassword())
			.build();
	}

} 