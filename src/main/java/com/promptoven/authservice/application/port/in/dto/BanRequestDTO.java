package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BanRequestDTO {
    private final String memberUUID;

    @Builder
    public BanRequestDTO(String memberUUID) {
        this.memberUUID = memberUUID;
    }
} 