package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SetMemberRoleRequestDTO {
    private final String memberNickname;
    private final String roleName;

    @Builder
    public SetMemberRoleRequestDTO(String memberNickname, String roleName) {
        this.memberNickname = memberNickname;
        this.roleName = roleName;
    }
} 