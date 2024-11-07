package com.promptoven.authservice.application.port.out.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class RefreshDTO {
	private String accessToken;
	private String nickname;
	private String role;
}
