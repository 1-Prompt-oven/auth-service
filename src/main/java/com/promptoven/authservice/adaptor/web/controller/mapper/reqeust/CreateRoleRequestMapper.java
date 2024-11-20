package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.CreateRoleRequestVO;
import com.promptoven.authservice.application.port.in.dto.CreateRoleRequestDTO;

public class CreateRoleRequestMapper {

	public static CreateRoleRequestDTO toDTO(CreateRoleRequestVO vo) {
		return CreateRoleRequestDTO.builder()
			.name(vo.getName())
			.description(vo.getDescription())
			.build();
	}
} 