package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SetMemberRoleRequestDTO {
    private final String memberNickname;
    private final String roleName;
}