package com.promptoven.authservice.adaptor.jpa.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "member", indexes = {
	@Index(name = "idx_uuid", columnList = "uuid"
	)}
)
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

	public void setId(Long id) {
		this.id = id;
	}

}
