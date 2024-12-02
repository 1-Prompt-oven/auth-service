package com.promptoven.authservice.adaptor.jpa;

import com.promptoven.authservice.adaptor.jpa.entity.SocialLoginInfoEntity;
import com.promptoven.authservice.application.service.dto.SocialLoginInfoDTO;

class JpaSocialLoginInfoDTOEntityMapper {

	static SocialLoginInfoDTO toDTO(SocialLoginInfoEntity entity) {
		return SocialLoginInfoDTO.builder()
			.memberUUID(entity.getMemberUUID())
			.provider(entity.getProvider())
			.providerID(entity.getProviderID())
			.build();
	}

	static SocialLoginInfoEntity toEntity(SocialLoginInfoDTO dto) {
		return SocialLoginInfoEntity.builder()
			.memberUUID(dto.getMemberUUID())
			.provider(dto.getProvider())
			.providerID(dto.getProviderID())
			.build();
	}

}
