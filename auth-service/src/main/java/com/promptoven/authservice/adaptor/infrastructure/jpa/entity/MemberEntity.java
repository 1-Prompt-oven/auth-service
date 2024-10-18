package com.promptoven.authservice.adaptor.infrastructure.jpa.entity;

import java.time.LocalDateTime;

import com.promptoven.authservice.domain.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
@Getter
@Table(name = "member")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid;
	private String email;
	private String password;
	private String nickname;
	private LocalDateTime createdAt;
	private Boolean isDeleted;
	private Boolean isBanned;
	private int role;

	public static MemberEntity fromDomain(Member member) {
		return MemberEntity.builder()
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
