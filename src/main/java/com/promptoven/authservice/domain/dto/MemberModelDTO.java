package com.promptoven.authservice.domain.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberModelDTO {

	private final String uuid;

	private final String email;
	private final String password;
	private final String nickname;

	private final LocalDateTime createdAt;
	private final Boolean isDeleted;
	private final Boolean isBanned;

	private final int role;

	@Builder
	public MemberModelDTO(String uuid, String email, String password, String nickname, LocalDateTime createdAt,
		Boolean isDeleted, Boolean isBanned, int role) {
		this.uuid = uuid;
		this.email = email;
		this.password = password;
		this.nickname = nickname;
		this.createdAt = createdAt;
		this.isDeleted = isDeleted;
		this.isBanned = isBanned;
		this.role = role;
	}
}
