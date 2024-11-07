package com.promptoven.authservice.application.port.out.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public record RefreshDTO(String accessToken, String nickname, String role) {

}
