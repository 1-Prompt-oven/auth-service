package com.promptoven.authservice.adaptor.infrastructure.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.promptoven.authservice.adaptor.infrastructure.jpa.entity.OauthInfoEntity;

public interface OauthInfoRepository extends JpaRepository<OauthInfoEntity, Long> {

	@Query("SELECT memberUUID FROM OauthInfoEntity WHERE provider = ?1 AND providerID = ?2")
	String findByProviderAndProviderID(String provider, String providerID);

	List<OauthInfoEntity> findAllByMemberUUID(String memberUUID);

	void deleteByMemberUUIDAndProviderAndProviderID(String memberUUID, String provider, String providerID);
}
