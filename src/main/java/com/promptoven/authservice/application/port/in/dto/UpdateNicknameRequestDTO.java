package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UpdateNicknameRequestDTO implements MemberUUIDOnlyDTO {
    private final String memberUUID;
    private final String nickname;

    @Override
    public String memberUUID() {
        return memberUUID;
    }
} 