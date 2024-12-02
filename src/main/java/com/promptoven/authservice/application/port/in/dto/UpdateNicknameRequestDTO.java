package com.promptoven.authservice.application.port.in.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UpdateNicknameRequestDTO {
	private final String memberUUID;
	private final String nickname;
    
	public String memberUUID() {
		return memberUUID;
	}
} 