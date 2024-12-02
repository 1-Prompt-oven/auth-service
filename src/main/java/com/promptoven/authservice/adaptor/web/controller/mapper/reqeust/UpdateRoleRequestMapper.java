package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.UpdateRoleRequestVO;
import com.promptoven.authservice.application.port.in.dto.UpdateRoleRequestDTO;

public class UpdateRoleRequestMapper {

	public static UpdateRoleRequestDTO toDTO(UpdateRoleRequestVO vo) {
		return UpdateRoleRequestDTO.builder()
			.id(vo.getId())
			.name(vo.getName())
			.description(vo.getDescription())
			.build();
	}
}
