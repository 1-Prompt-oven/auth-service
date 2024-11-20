package com.promptoven.authservice.adaptor.jpa;

import com.promptoven.authservice.adaptor.jpa.entity.OauthInfoEntity;
import com.promptoven.authservice.application.service.dto.OauthInfoDTO;

class JpaOauthInfoDTOEntityMapper {

	static OauthInfoDTO toDTO(OauthInfoEntity entity) {
		return OauthInfoDTO.builder()
			.memberUUID(entity.getMemberUUID())
			.provider(entity.getProvider())
			.providerID(entity.getProviderID())
			.build();
	}

	static OauthInfoEntity toEntity(OauthInfoDTO dto) {
		return OauthInfoEntity.builder()
			.memberUUID(dto.getMemberUUID())
			.provider(dto.getProvider())
			.providerID(dto.getProviderID())
			.build();
	}

}
