package com.promptoven.authservice.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RoleModelDTO {
	private final String name;
	private final int id;
	private final String description;

	@Builder
	public RoleModelDTO(String name, int id, String description) {
		this.name = name;
		this.id = id;
		this.description = description;
	}
}
