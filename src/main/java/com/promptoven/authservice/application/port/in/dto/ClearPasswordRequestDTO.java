package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClearPasswordRequestDTO {
    private final String memberUUID;

    @Builder
    public ClearPasswordRequestDTO(String memberUUID) {
        this.memberUUID = memberUUID;
    }
} 