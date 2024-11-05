package com.promptoven.authservice.adaptor.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.promptoven.authservice.adaptor.infrastructure.jpa.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

	@Query("SELECT m FROM MemberEntity m WHERE m.email = :email and m.isDeleted = false")
	MemberEntity findByEmail(String email);

	@Query("SELECT m FROM MemberEntity m WHERE m.uuid = :uuid and m.isDeleted = false")
	MemberEntity findByUuid(String uuid);

	@Query("SELECT m FROM MemberEntity m WHERE m.nickname = :nickname and m.isDeleted = false")
	MemberEntity findByNickname(String nickname);

	@Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MemberEntity m WHERE m.email = :email and m.isDeleted = false")
	boolean existsByEmail(String email);

	@Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM MemberEntity m WHERE m.nickname = :nickname and m.isDeleted = false")
	boolean existsByNickname(String nickname);
}