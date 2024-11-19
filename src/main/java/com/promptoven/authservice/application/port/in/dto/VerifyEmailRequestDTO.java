package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class VerifyEmailRequestDTO {
    private final String email;

    @Builder
    public VerifyEmailRequestDTO(String email) {
        this.email = email;
    }
} 