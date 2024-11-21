package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class VerifyEmailRequestDTO {
    private final String email;
}