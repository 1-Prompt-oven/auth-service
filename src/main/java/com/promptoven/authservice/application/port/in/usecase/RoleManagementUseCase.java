package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.in.dto.CreateRoleRequestDTO;
import com.promptoven.authservice.application.port.in.dto.DeleteRoleRequestDTO;
import com.promptoven.authservice.application.port.in.dto.UpdateRoleRequestDTO;

public interface RoleManagementUseCase {

	void createRole(CreateRoleRequestDTO createRoleRequestDTO);

	void deleteRole(DeleteRoleRequestDTO deleteRoleRequestDTO);

	void updateRole(UpdateRoleRequestDTO updateRoleRequestDTO);
}
