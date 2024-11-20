package com.promptoven.authservice.application.port.in.dto;

import org.springframework.lang.Nullable;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SocialLoginRequestDTO {
    private final String provider;
    private final String providerId;
    private final @Nullable String email;
} 