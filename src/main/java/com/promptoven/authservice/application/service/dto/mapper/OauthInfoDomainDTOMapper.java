package com.promptoven.authservice.application.service.dto.mapper;

import org.springframework.stereotype.Component;

import com.promptoven.authservice.application.service.dto.SocialLoginInfoDTO;
import com.promptoven.authservice.domain.SocialLoginInfo;

@Component
public class OauthInfoDomainDTOMapper implements DomainDTOMapper<SocialLoginInfo, SocialLoginInfoDTO> {

	@Override
	public SocialLoginInfoDTO toDTO(SocialLoginInfo domain) {
		return SocialLoginInfoDTO.builder()
			.memberUUID(domain.getMemberUUID())
			.provider(domain.getProvider())
			.providerID(domain.getProviderID())
			.build();
	}

	@Override
	public SocialLoginInfo toDomain(SocialLoginInfoDTO dto) {
		return SocialLoginInfo.builder()
			.memberUUID(dto.getMemberUUID())
			.provider(dto.getProvider())
			.providerID(dto.getProviderID())
			.build();
	}
}
