package com.promptoven.authservice.application.port.out.dto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberUnbanEvent {

	private final String memberUUID;
}
