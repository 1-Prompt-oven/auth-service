package com.promptoven.authservice.application.port.out.call;

import com.promptoven.authservice.domain.Role;

public interface RolePersistence {

	void create(Role role);

	Role findRoleById(int roleID);

}
