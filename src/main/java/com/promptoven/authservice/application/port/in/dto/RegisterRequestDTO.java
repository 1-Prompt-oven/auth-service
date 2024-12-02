package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class RegisterRequestDTO {
    private final String email;
    private final String password;
    private final String nickname;
}
