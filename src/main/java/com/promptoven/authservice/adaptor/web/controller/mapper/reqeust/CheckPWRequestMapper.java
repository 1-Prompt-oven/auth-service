package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.CheckPWRequestVO;
import com.promptoven.authservice.application.port.in.dto.CheckPWRequestDTO;

public class CheckPWRequestMapper {

	public static CheckPWRequestDTO toDTO(CheckPWRequestVO vo) {
		return CheckPWRequestDTO.builder()
			.memberUUID(vo.getMemberUUID())
			.password(vo.getPassword())
			.build();
	}

} 