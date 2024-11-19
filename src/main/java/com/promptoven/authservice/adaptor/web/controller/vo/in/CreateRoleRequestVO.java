package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.CreateRoleRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoleRequestVO {

	private String name;

	private String description;

	public CreateRoleRequestDTO toDTO() {
		return CreateRoleRequestDTO.builder()
			.name(name)
			.description(description)
			.build();
	}
}
