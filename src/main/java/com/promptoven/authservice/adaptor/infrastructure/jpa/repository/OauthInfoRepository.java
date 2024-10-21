package com.promptoven.authservice.adaptor.infrastructure.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.promptoven.authservice.adaptor.infrastructure.jpa.entity.OauthInfoEntity;

public interface OauthInfoRepository extends JpaRepository<OauthInfoEntity, Long> {

	@Query("SELECT memberUUID FROM OauthInfoEntity WHERE provider = ?1 AND providerID = ?2")
	String findByProviderAndProviderID(String provider, String providerID);

	List<OauthInfoEntity> findAllByMemberUUID(String memberUUID);

	@Modifying
	@Query("DELETE FROM OauthInfoEntity o WHERE o.memberUUID = :memberUUID AND o.provider = :provider AND o.providerID = :providerID")
	void deleteByMemberUUIDAndProviderAndProviderID(@Param("memberUUID") String memberUUID, @Param("provider") String provider, @Param("providerID") String providerID);
}
