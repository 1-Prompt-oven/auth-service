package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.UpdateRoleRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRoleRequestVO {

	private int id;
	private String name;
	private String description;

	public UpdateRoleRequestDTO toDTO() {
		return UpdateRoleRequestDTO.builder()
			.id(id)
			.name(name)
			.description(description)
			.build();
	}
}
