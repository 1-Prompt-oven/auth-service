package com.promptoven.authservice.application.service;

import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.dto.CreateRoleRequestDTO;
import com.promptoven.authservice.application.port.in.dto.DeleteRoleRequestDTO;
import com.promptoven.authservice.application.port.in.dto.UpdateRoleRequestDTO;
import com.promptoven.authservice.application.port.in.usecase.RoleManagementUseCase;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.domain.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleManagementService implements RoleManagementUseCase {

	private final RolePersistence rolePersistence;

	@Override
	public void createRole(CreateRoleRequestDTO createRoleRequestDTO) {
		int newRoleID = 1 + rolePersistence.findMaxRoleID();
		rolePersistence.create(Role.createRole(createRoleRequestDTO.getName(), newRoleID,
			createRoleRequestDTO.getDescription()));
	}

	@Override
	public void deleteRole(DeleteRoleRequestDTO deleteRoleRequestDTO) {
		rolePersistence.deleteRoleById(deleteRoleRequestDTO.getRoleId());
	}

	@Override
	public void updateRole(UpdateRoleRequestDTO updateRoleRequestDTO) {
		Role role = rolePersistence.findRoleById(updateRoleRequestDTO.getId());
		rolePersistence.updateRole(
			Role.updateRole(role, updateRoleRequestDTO.getName(), updateRoleRequestDTO.getDescription()));
	}
}
