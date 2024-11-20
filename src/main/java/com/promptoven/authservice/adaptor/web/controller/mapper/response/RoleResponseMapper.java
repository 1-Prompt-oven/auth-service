package com.promptoven.authservice.adaptor.web.controller.mapper.response;

import com.promptoven.authservice.adaptor.web.controller.vo.out.RoleResponseVO;
import com.promptoven.authservice.application.service.dto.RoleDTO;

public class RoleResponseMapper {

	public static RoleResponseVO fromDTO(RoleDTO dto) {
		return RoleResponseVO.builder()
			.id(dto.getId())
			.name(dto.getName())
			.description(dto.getDescription())
			.build();
	}
}
