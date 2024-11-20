package com.promptoven.authservice.adaptor.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promptoven.authservice.adaptor.jpa.entity.OauthInfoEntity;
import com.promptoven.authservice.adaptor.jpa.repository.OauthInfoRepository;
import com.promptoven.authservice.application.port.out.call.OauthInfoPersistence;
import com.promptoven.authservice.application.service.dto.OauthInfoDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OauthPersistenceWithJpa implements OauthInfoPersistence {
	private final OauthInfoRepository oauthInfoRepository;

	@Override
	public boolean existByOauthInfoDTO(OauthInfoDTO oauthInfoDTO) {
		return oauthInfoRepository.existsByMemberUUIDAndProviderAndProviderID(
			oauthInfoDTO.getMemberUUID(),
			oauthInfoDTO.getProvider(),
			oauthInfoDTO.getProviderID());
	}

	@Override
	public void recordOauthInfo(OauthInfoDTO oauthInfoDTO) {
		oauthInfoRepository.save(JpaOauthInfoDTOEntityMapper.toEntity(oauthInfoDTO));
	}

	@Override
	public String getMemberUUID(String Provider, String ProviderID) {
		return oauthInfoRepository.findByProviderAndProviderID(Provider, ProviderID);
	}

	@Override
	public List<OauthInfoDTO> getOauthInfo(String memberUUID) {
		List<OauthInfoEntity> oauthInfoEntities = oauthInfoRepository.findAllByMemberUUID(memberUUID);
		return oauthInfoEntities.stream()
			.map(JpaOauthInfoDTOEntityMapper::toDTO)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteOauthInfo(OauthInfoDTO oauthInfoDTO) {
		String memberUUID = oauthInfoDTO.getMemberUUID();
		String provider = oauthInfoDTO.getProvider();
		String providerID = oauthInfoDTO.getProviderID();
		oauthInfoRepository.deleteByMemberUUIDAndProviderAndProviderID(memberUUID, provider, providerID);
	}
}
