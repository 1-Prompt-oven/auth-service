package com.promptoven.authservice.application.port.out.call;

import com.promptoven.authservice.application.service.dto.RoleDTO;

public interface RolePersistence {

	void create(RoleDTO roleDTO);

	RoleDTO findRoleById(int roleID);

	RoleDTO findByName(String roleName);

	int findMaxRoleID();

	void deleteRoleById(int roleID);

	void updateRole(RoleDTO roleDTO);

	long count();
}
