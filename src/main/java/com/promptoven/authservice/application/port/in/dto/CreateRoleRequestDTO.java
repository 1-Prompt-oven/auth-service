package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateRoleRequestDTO {
    private final String name;
    private final String description;

    @Builder
    public CreateRoleRequestDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
} 