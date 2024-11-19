package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OauthRegisterRequestDTO {
    private final String provider;
    private final String providerId;
    private final String memberUUID;

    @Builder
    public OauthRegisterRequestDTO(String provider, String providerId, String memberUUID) {
        this.provider = provider;
        this.providerId = providerId;
        this.memberUUID = memberUUID;
    }
} 