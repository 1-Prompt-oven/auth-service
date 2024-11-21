package com.promptoven.authservice.adaptor.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promptoven.authservice.adaptor.jpa.entity.SocialLoginInfoEntity;
import com.promptoven.authservice.adaptor.jpa.repository.SocialLoginInfoRepository;
import com.promptoven.authservice.application.port.out.call.SocialLoginInfoPersistence;
import com.promptoven.authservice.application.service.dto.SocialLoginInfoDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class SocialLoginPersistenceWithJpa implements SocialLoginInfoPersistence {
	private final SocialLoginInfoRepository socialLoginInfoRepository;

	@Override
	public boolean existBySocialLoginDTO(SocialLoginInfoDTO socialLoginInfoDTO) {
		return socialLoginInfoRepository.existsByMemberUUIDAndProviderAndProviderID(
			socialLoginInfoDTO.getMemberUUID(),
			socialLoginInfoDTO.getProvider(),
			socialLoginInfoDTO.getProviderID());
	}

	@Override
	public void recordSocialLoginInfo(SocialLoginInfoDTO socialLoginInfoDTO) {
		socialLoginInfoRepository.save(JpaSocialLoginInfoDTOEntityMapper.toEntity(socialLoginInfoDTO));
	}

	@Override
	public String getMemberUUID(String Provider, String ProviderID) {
		return socialLoginInfoRepository.findByProviderAndProviderID(Provider, ProviderID);
	}

	@Override
	public List<SocialLoginInfoDTO> getSocialLoginInfo(String memberUUID) {
		List<SocialLoginInfoEntity> oauthInfoEntities = socialLoginInfoRepository.findAllByMemberUUID(memberUUID);
		return oauthInfoEntities.stream()
			.map(JpaSocialLoginInfoDTOEntityMapper::toDTO)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteSocialLoginInfo(SocialLoginInfoDTO socialLoginInfoDTO) {
		String memberUUID = socialLoginInfoDTO.getMemberUUID();
		String provider = socialLoginInfoDTO.getProvider();
		String providerID = socialLoginInfoDTO.getProviderID();
		socialLoginInfoRepository.deleteByMemberUUIDAndProviderAndProviderID(memberUUID, provider, providerID);
	}
}
