package com.promptoven.authservice.application.service.dto.mapper;

import org.springframework.stereotype.Component;

import com.promptoven.authservice.application.service.dto.RoleDTO;
import com.promptoven.authservice.domain.Role;

@Component
public class RoleDomainDTOMapper implements DomainDTOMapper<Role, RoleDTO> {

	@Override
	public RoleDTO toDTO(Role domain) {
		return RoleDTO.builder()
			.id(domain.getId())
			.name(domain.getName())
			.description(domain.getDescription())
			.build();
	}

	@Override
	public Role toDomain(RoleDTO dto) {
		return Role.builder()
			.id(dto.getId())
			.name(dto.getName())
			.description(dto.getDescription())
			.build();
	}
}
