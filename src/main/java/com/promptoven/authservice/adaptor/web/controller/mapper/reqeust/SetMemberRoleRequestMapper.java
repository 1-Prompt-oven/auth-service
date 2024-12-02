package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.SetMemberRoleRequestVO;
import com.promptoven.authservice.application.port.in.dto.SetMemberRoleRequestDTO;

public class SetMemberRoleRequestMapper {

	public static SetMemberRoleRequestDTO toDTO(SetMemberRoleRequestVO vo) {
		return SetMemberRoleRequestDTO.builder()
			.memberNickname(vo.getMemberNickname())
			.roleName(vo.getRoleName())
			.build();
	}
} 