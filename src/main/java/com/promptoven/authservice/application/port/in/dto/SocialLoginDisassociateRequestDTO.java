package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SocialLoginDisassociateRequestDTO {
    private final String provider;
    private final String providerId;
    private final String memberUUID;
}