package com.promptoven.authservice.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;

@Builder
@AllArgsConstructor
@Getter
public class UnbanRequestDTO implements MemberUUIDOnlyDTO {
    private final String memberUUID;

    @Override
    public String memberUUID() {
        return memberUUID;
    }
}
