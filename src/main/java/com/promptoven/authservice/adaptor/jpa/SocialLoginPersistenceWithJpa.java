package com.promptoven.authservice.adaptor.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promptoven.authservice.adaptor.jpa.entity.OauthInfoEntity;
import com.promptoven.authservice.adaptor.jpa.repository.OauthInfoRepository;
import com.promptoven.authservice.application.port.out.call.SocialLoginInfoPersistence;
import com.promptoven.authservice.application.service.dto.SocialLoginInfoDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SocialLoginPersistenceWithJpa implements SocialLoginInfoPersistence {
	private final OauthInfoRepository oauthInfoRepository;

	@Override
	public boolean existByOauthInfoDTO(SocialLoginInfoDTO socialLoginInfoDTO) {
		return oauthInfoRepository.existsByMemberUUIDAndProviderAndProviderID(
			socialLoginInfoDTO.getMemberUUID(),
			socialLoginInfoDTO.getProvider(),
			socialLoginInfoDTO.getProviderID());
	}

	@Override
	public void recordOauthInfo(SocialLoginInfoDTO socialLoginInfoDTO) {
		oauthInfoRepository.save(JpaOauthInfoDTOEntityMapper.toEntity(socialLoginInfoDTO));
	}

	@Override
	public String getMemberUUID(String Provider, String ProviderID) {
		return oauthInfoRepository.findByProviderAndProviderID(Provider, ProviderID);
	}

	@Override
	public List<SocialLoginInfoDTO> getOauthInfo(String memberUUID) {
		List<OauthInfoEntity> oauthInfoEntities = oauthInfoRepository.findAllByMemberUUID(memberUUID);
		return oauthInfoEntities.stream()
			.map(JpaOauthInfoDTOEntityMapper::toDTO)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteOauthInfo(SocialLoginInfoDTO socialLoginInfoDTO) {
		String memberUUID = socialLoginInfoDTO.getMemberUUID();
		String provider = socialLoginInfoDTO.getProvider();
		String providerID = socialLoginInfoDTO.getProviderID();
		oauthInfoRepository.deleteByMemberUUIDAndProviderAndProviderID(memberUUID, provider, providerID);
	}
}
