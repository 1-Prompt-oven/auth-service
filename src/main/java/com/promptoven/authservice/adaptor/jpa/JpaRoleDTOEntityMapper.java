package com.promptoven.authservice.adaptor.jpa;

import com.promptoven.authservice.adaptor.jpa.entity.RoleEntity;
import com.promptoven.authservice.application.service.dto.RoleDTO;

class JpaRoleDTOEntityMapper {

	static RoleDTO toDTO(RoleEntity entity) {
		return RoleDTO.builder()
			.id(entity.getId())
			.name(entity.getName())
			.description(entity.getDescription())
			.build();
	}

	static RoleEntity toEntity(RoleDTO dto) {
		return RoleEntity.builder()
			.id(dto.getId())
			.name(dto.getName())
			.description(dto.getDescription())
			.build();
	}
}
