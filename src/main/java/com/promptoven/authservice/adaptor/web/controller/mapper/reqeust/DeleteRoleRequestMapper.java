package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.DeleteRoleRequestVO;
import com.promptoven.authservice.application.port.in.dto.DeleteRoleRequestDTO;

public class DeleteRoleRequestMapper {

	public static DeleteRoleRequestDTO toDTO(DeleteRoleRequestVO vo) {
		return DeleteRoleRequestDTO.builder()
			.roleId(vo.getRoleID())
			.build();
	}

}