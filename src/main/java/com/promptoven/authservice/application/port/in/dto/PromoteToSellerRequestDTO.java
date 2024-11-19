package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PromoteToSellerRequestDTO {
    private final String memberUUID;

    @Builder
    public PromoteToSellerRequestDTO(String memberUUID) {
        this.memberUUID = memberUUID;
    }
}