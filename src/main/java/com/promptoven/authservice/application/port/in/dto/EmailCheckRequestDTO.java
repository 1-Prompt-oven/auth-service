package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EmailCheckRequestDTO {
    private final String email;
    private final String code;
}
