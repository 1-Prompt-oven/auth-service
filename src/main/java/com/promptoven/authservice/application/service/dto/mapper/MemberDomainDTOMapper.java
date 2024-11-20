package com.promptoven.authservice.application.service.dto.mapper;

import org.springframework.stereotype.Component;

import com.promptoven.authservice.application.service.dto.MemberDTO;
import com.promptoven.authservice.domain.Member;

@Component
public class MemberDomainDTOMapper implements DomainDTOMapper<Member, MemberDTO> {

	@Override
	public MemberDTO toDTO(Member domain) {
		return MemberDTO.builder()
			.uuid(domain.getUuid())
			.email(domain.getEmail())
			.nickname(domain.getNickname())
			.password(domain.getPassword())
			.createdAt(domain.getCreatedAt())
			.isDeleted(domain.getIsDeleted())
			.isBanned(domain.getIsBanned())
			.role(domain.getRole())
			.build();
	}

	@Override
	public Member toDomain(MemberDTO dto) {
		return Member.builder()
			.uuid(dto.getUuid())
			.email(dto.getEmail())
			.nickname(dto.getNickname())
			.password(dto.getPassword())
			.createdAt(dto.getCreatedAt())
			.isDeleted(dto.getIsDeleted())
			.isBanned(dto.getIsBanned())
			.role(dto.getRole())
			.build();
	}
}
