package com.promptoven.authservice.application.port.out.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

//todo: 내일 수업 듣고 DTO 어디로 가야 할 지 다시 정하기

@Getter
@Builder
@AllArgsConstructor
public class LoginDTO {

	private final String accessToken;
	private final String refreshToken;
	private final String role;
	private final String uuid;
	private final String nickname;

}
