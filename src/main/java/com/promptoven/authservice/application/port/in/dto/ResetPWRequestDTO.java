package com.promptoven.authservice.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ResetPWRequestDTO {

    private final String email;
    private final String password;

}
