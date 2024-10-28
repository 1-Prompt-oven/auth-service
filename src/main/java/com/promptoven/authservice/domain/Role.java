package com.promptoven.authservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
	private String name;
	private int id;
	private String description;

	public static Role createRole(String name, int id, String description) {
		return Role.builder()
			.name(name)
			.id(id)
			.description(description)
			.build();
	}

	public static Role updateRole(Role role, String name, String description) {
		return Role.builder()
				.name(name)
				.id(role.getId())
				.description(description)
				.build();
	}

}
