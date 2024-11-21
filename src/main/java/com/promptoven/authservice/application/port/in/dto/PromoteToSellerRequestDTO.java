package com.promptoven.authservice.application.port.in.dto;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PromoteToSellerRequestDTO implements MemberUUIDOnlyDTO {
    private final String memberUUID;

    @Override
    public String memberUUID() {
        return memberUUID;
    }
}
