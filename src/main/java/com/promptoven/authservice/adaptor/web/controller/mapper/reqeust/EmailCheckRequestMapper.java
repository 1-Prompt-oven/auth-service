package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.EmailCheckRequestVO;
import com.promptoven.authservice.application.port.in.dto.EmailCheckRequestDTO;

public class EmailCheckRequestMapper {

	public static EmailCheckRequestDTO toDTO(EmailCheckRequestVO vo) {
		return EmailCheckRequestDTO.builder()
			.email(vo.getEmail())
			.code(vo.getCode())
			.build();
	}

} 