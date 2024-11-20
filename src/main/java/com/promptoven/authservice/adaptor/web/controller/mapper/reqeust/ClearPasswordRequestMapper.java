package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.ClearPasswordRequestVO;
import com.promptoven.authservice.application.port.in.dto.ClearPasswordRequestDTO;

public class ClearPasswordRequestMapper {

	public static ClearPasswordRequestDTO toDTO(ClearPasswordRequestVO vo) {
		return ClearPasswordRequestDTO.builder()
			.memberUUID(vo.getMemberUUID())
			.build();
	}

}