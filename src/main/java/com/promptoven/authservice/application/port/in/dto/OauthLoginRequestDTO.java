package com.promptoven.authservice.application.port.in.dto;

import org.springframework.lang.Nullable;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthLoginRequestDTO {
    private final String provider;
    private final String providerId;
    private final @Nullable String email;

    @Builder
    public OauthLoginRequestDTO(String provider, String providerId, @Nullable String email) {
        this.provider = provider;
        this.providerId = providerId;
        this.email = email;
    }
} 