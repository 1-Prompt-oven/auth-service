package com.promptoven.authservice.domain;

import com.promptoven.authservice.domain.dto.RoleModelDTO;

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

	public static Role createRole(RoleModelDTO roleModelDTO) {
		return Role.builder()
			.name(roleModelDTO.getName())
			.id(roleModelDTO.getId())
			.description(roleModelDTO.getDescription())
			.build();
	}

	public static Role updateRole(Role role, RoleModelDTO newRoleModelDTO) {
		return Role.builder()
			.name(newRoleModelDTO.getName())
			.id(role.getId())
			.description(newRoleModelDTO.getDescription())
			.build();
	}

}
