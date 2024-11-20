package com.promptoven.authservice.application.service.dto.mapper;

import org.springframework.stereotype.Component;

import com.promptoven.authservice.application.service.dto.OauthInfoDTO;
import com.promptoven.authservice.domain.OauthInfo;

@Component
public class OauthInfoDomainDTOMapper implements DomainDTOMapper<OauthInfo, OauthInfoDTO> {

	@Override
	public OauthInfoDTO toDTO(OauthInfo domain) {
		return OauthInfoDTO.builder()
			.memberUUID(domain.getMemberUUID())
			.provider(domain.getProvider())
			.providerID(domain.getProviderID())
			.build();
	}

	@Override
	public OauthInfo toDomain(OauthInfoDTO dto) {
		return OauthInfo.builder()
			.memberUUID(dto.getMemberUUID())
			.provider(dto.getProvider())
			.providerID(dto.getProviderID())
			.build();
	}
}
