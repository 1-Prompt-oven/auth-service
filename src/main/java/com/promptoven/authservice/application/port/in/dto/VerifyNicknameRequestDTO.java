package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VerifyNicknameRequestDTO {
    private final String nickname;

    @Builder
    public VerifyNicknameRequestDTO(String nickname) {
        this.nickname = nickname;
    }
} 