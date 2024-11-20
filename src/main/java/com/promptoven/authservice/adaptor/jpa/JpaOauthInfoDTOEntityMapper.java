package com.promptoven.authservice.adaptor.jpa;

import com.promptoven.authservice.adaptor.jpa.entity.OauthInfoEntity;
import com.promptoven.authservice.application.service.dto.SocialLoginInfoDTO;

class JpaOauthInfoDTOEntityMapper {

	static SocialLoginInfoDTO toDTO(OauthInfoEntity entity) {
		return SocialLoginInfoDTO.builder()
			.memberUUID(entity.getMemberUUID())
			.provider(entity.getProvider())
			.providerID(entity.getProviderID())
			.build();
	}

	static OauthInfoEntity toEntity(SocialLoginInfoDTO dto) {
		return OauthInfoEntity.builder()
			.memberUUID(dto.getMemberUUID())
			.provider(dto.getProvider())
			.providerID(dto.getProviderID())
			.build();
	}

}
