package com.promptoven.authservice.application.port.out.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberWithdrawEvent {

	private final String memberUUID;
}
