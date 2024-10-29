package com.promptoven.authservice.application.service;

import com.promptoven.authservice.application.port.in.usecase.RoleManagementUseCase;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleManagementService implements RoleManagementUseCase {

    private final RolePersistence rolePersistence;

    @Override
    public void createRole(String name, @Nullable String description) {
        int newRoleID = 1 + rolePersistence.findMaxRoleID();
        rolePersistence.create(Role.createRole(name, newRoleID, description));
    }

    @Override
    public void deleteRole(int ID) {
        rolePersistence.deleteRoleById(ID);
    }

    @Override
    public void updateRole(int ID, String name, String description) {
        Role role = rolePersistence.findRoleById(ID);
        rolePersistence.updateRole(Role.updateRole(role, name, description));
    }
}
