package com.promptoven.authservice.application.service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RoleDTO {

	private final String name;
	private final String description;
	private final int id;

	@Builder
	public RoleDTO(String name, String description, int id) {
		this.name = name;
		this.description = description;
		this.id = id;
	}
}
