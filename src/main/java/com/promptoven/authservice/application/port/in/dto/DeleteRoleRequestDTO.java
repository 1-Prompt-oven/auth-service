package com.promptoven.authservice.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class DeleteRoleRequestDTO {
    private final int roleId;
}