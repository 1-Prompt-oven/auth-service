package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteRoleRequestDTO {
    private final int roleId;

    @Builder
    public DeleteRoleRequestDTO(int roleId) {
        this.roleId = roleId;
    }
} 