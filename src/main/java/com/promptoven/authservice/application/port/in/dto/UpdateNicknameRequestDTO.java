package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateNicknameRequestDTO {
    private final String memberUUID;
    private final String nickname;

    @Builder
    public UpdateNicknameRequestDTO(String memberUUID, String nickname) {
        this.memberUUID = memberUUID;
        this.nickname = nickname;
    }
} 