package com.promptoven.authservice.adaptor.jpa;

import com.promptoven.authservice.adaptor.jpa.entity.MemberEntity;
import com.promptoven.authservice.application.service.dto.MemberDTO;

class JpaMemberDTOEntityMapper {

	static MemberDTO toDTO(MemberEntity entity) {
		return MemberDTO.builder()
			.uuid(entity.getUuid())
			.email(entity.getEmail())
			.password(entity.getPassword())
			.nickname(entity.getNickname())
			.role(entity.getRole())
			.isDeleted(entity.getIsDeleted())
			.isBanned(entity.getIsBanned())
			.createdAt(entity.getCreatedAt())
			.build();
	}

	static MemberEntity toEntity(MemberDTO dto) {
		return MemberEntity.builder()
			.uuid(dto.getUuid())
			.email(dto.getEmail())
			.password(dto.getPassword())
			.nickname(dto.getNickname())
			.role(dto.getRole())
			.isDeleted(dto.getIsDeleted())
			.isBanned(dto.getIsBanned())
			.createdAt(dto.getCreatedAt())
			.build();
	}
}
