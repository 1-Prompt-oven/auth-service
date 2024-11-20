package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CreateRoleRequestDTO {
    private final String name;
    private final String description;
}