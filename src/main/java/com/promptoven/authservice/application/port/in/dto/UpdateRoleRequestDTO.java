package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UpdateRoleRequestDTO {
    private final int id;
    private final String name;
    private final String description;
}