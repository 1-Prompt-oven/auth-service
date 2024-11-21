package com.promptoven.authservice.adaptor.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.promptoven.authservice.adaptor.jpa.entity.SocialLoginInfoEntity;

public interface SocialLoginInfoRepository extends JpaRepository<SocialLoginInfoEntity, Long> {

	@Query("SELECT memberUUID FROM SocialLoginInfoEntity WHERE provider = ?1 AND providerID = ?2")
	String findByProviderAndProviderID(String provider, String providerID);

	List<SocialLoginInfoEntity> findAllByMemberUUID(String memberUUID);

	@Modifying
	@Query("DELETE FROM SocialLoginInfoEntity o WHERE o.memberUUID = :memberUUID AND o.provider = :provider AND o.providerID = :providerID")
	void deleteByMemberUUIDAndProviderAndProviderID(@Param("memberUUID") String memberUUID,
		@Param("provider") String provider, @Param("providerID") String providerID);

	@Query("SELECT CASE WHEN COUNT(o) > 0 THEN true ELSE false END FROM SocialLoginInfoEntity o WHERE o.memberUUID = :memberUUID AND o.provider = :provider AND o.providerID = :providerID")
	boolean existsByMemberUUIDAndProviderAndProviderID(String memberUUID, String provider, String providerID);
}
