package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.BanRequestVO;
import com.promptoven.authservice.application.port.in.dto.BanRequestDTO;

public class BanRequestMapper {

	public static BanRequestDTO toDTO(BanRequestVO vo) {
		return BanRequestDTO.builder()
			.memberUUID(vo.getMemberUUID())
			.build();
	}
} 