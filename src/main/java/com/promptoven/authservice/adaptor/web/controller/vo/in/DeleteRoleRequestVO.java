package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.DeleteRoleRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteRoleRequestVO {

	private int roleID;

	public DeleteRoleRequestDTO toDTO() {
		return DeleteRoleRequestDTO.builder()
			.roleId(roleID)
			.build();
	}
}
