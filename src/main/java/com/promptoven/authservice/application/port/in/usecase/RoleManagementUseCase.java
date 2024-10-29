package com.promptoven.authservice.application.port.in.usecase;

import org.springframework.lang.Nullable;

public interface RoleManagementUseCase {

    void createRole(String name, @Nullable String description);

    void deleteRole(int ID);

    void updateRole(int ID, String name, String description);
}
