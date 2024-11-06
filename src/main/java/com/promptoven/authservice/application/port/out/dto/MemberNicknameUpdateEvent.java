package com.promptoven.authservice.application.port.out.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberNicknameUpdateEvent {

	private final String memberId;
	private final String newNickname;

}
