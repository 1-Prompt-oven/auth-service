package com.promptoven.authservice.adaptor.infrastructure.jpa.entity;

import java.time.LocalDateTime;

import com.promptoven.authservice.domain.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberEntity {

	private String uuid;
	private String email;
	private String password;
	private String nickname;
	private LocalDateTime createdAt;
	private Boolean isDeleted;
	private Boolean isBanned;
	private int role;

	public MemberEntity(Member member) {
		MemberEntity.builder()
			.uuid(member.getUuid())
			.email(member.getEmail())
			.password(member.getPassword())
			.nickname(member.getNickname())
			.createdAt(member.getCreatedAt())
			.isDeleted(member.getIsDeleted())
			.isBanned(member.getIsBanned())
			.role(member.getRole())
			.build();
	}

	public Member toDomain() {
		return Member.builder()
			.uuid(this.uuid)
			.email(this.email)
			.password(this.password)
			.nickname(this.nickname)
			.createdAt(this.createdAt)
			.isDeleted(this.isDeleted)
			.isBanned(this.isBanned)
			.role(this.role)
			.build();
	}
}
