package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateRoleRequestDTO {
    private final int id;
    private final String name;
    private final String description;

    @Builder
    public UpdateRoleRequestDTO(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
} 