package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.SetMemberRoleRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SetMemberRoleRequestVO {

	private String memberNickname;
	private String roleName;

	public SetMemberRoleRequestDTO toDTO() {
		return SetMemberRoleRequestDTO.builder()
			.memberNickname(memberNickname)
			.roleName(roleName)
			.build();
	}
}
