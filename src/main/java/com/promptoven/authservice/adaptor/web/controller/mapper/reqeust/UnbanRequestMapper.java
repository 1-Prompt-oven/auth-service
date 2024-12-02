package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.UnbanRequestVO;
import com.promptoven.authservice.application.port.in.dto.UnbanRequestDTO;

public class UnbanRequestMapper {

	public static UnbanRequestDTO toDTO(UnbanRequestVO vo) {
		return UnbanRequestDTO.builder()
			.memberUUID(vo.getMemberUUID())
			.build();
	}
}
