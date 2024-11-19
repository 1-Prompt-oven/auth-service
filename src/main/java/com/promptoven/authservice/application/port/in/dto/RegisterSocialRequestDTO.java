package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RegisterSocialRequestDTO {
    private final String email;
    private final String password;
    private final String nickname;
    private final String provider;
    private final String providerId;

    @Builder
    public RegisterSocialRequestDTO(String email, String password, String nickname, 
                                  String provider, String providerId) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.provider = provider;
        this.providerId = providerId;
    }
} 