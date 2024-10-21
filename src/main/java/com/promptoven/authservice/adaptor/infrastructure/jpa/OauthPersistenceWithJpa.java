package com.promptoven.authservice.adaptor.infrastructure.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promptoven.authservice.adaptor.infrastructure.jpa.entity.OauthInfoEntity;
import com.promptoven.authservice.adaptor.infrastructure.jpa.repository.OauthInfoRepository;
import com.promptoven.authservice.application.port.out.call.OauthInfoPersistence;
import com.promptoven.authservice.domain.OauthInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OauthPersistenceWithJpa implements OauthInfoPersistence {

	private final OauthInfoRepository oauthInfoRepository;

	@Override
	public void recordOauthInfo(OauthInfo oauthInfo) {
		oauthInfoRepository.save(OauthInfoEntity.fromDomain(oauthInfo));
	}

	@Override
	public String getMemberUUID(String Provider, String ProviderID) {
		return oauthInfoRepository.findByProviderAndProviderID(Provider, ProviderID);
	}

	@Override
	public List<OauthInfo> getOauthInfo(String memberUUID) {
		List<OauthInfoEntity> oauthInfoEntities = oauthInfoRepository.findAllByMemberUUID(memberUUID);
		return oauthInfoEntities.stream()
			.map(OauthInfoEntity::toDomain)
			.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteOauthInfo(String memberUUID, String provider, String providerID) {
		oauthInfoRepository.deleteByMemberUUIDAndProviderAndProviderID(memberUUID, provider, providerID);
	}
}
