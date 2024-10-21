package com.promptoven.authservice.adaptor.infrastructure.jpa;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.promptoven.authservice.adaptor.infrastructure.jpa.entity.MemberEntity;
import com.promptoven.authservice.adaptor.infrastructure.jpa.entity.OauthInfoEntity;
import com.promptoven.authservice.adaptor.infrastructure.jpa.entity.RoleEntity;
import com.promptoven.authservice.adaptor.infrastructure.jpa.repository.MemberRepository;
import com.promptoven.authservice.adaptor.infrastructure.jpa.repository.OauthInfoRepository;
import com.promptoven.authservice.adaptor.infrastructure.jpa.repository.RoleRepository;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.OauthInfoPersistence;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.OauthInfo;
import com.promptoven.authservice.domain.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component("adapterAuthPersistenceWithJpa")
public class AuthPersistenceImplByJpa implements MemberPersistence, OauthInfoPersistence, RolePersistence {

	private final MemberRepository memberRepository;
	private final RoleRepository roleRepository;
	private final OauthInfoRepository oauthInfoRepository;

	@Override
	public void create(Member member) {
		MemberEntity memberEntity = MemberEntity.fromDomain(member);
		memberRepository.save(memberEntity);
	}

	@Override
	public Member findByEmail(String email) {
		MemberEntity memberEntity = memberRepository.findByEmail(email);
		return memberEntity.toDomain();
	}

	@Override
	public Member findByUuid(String uuid) {
		MemberEntity memberEntity = memberRepository.findByUuid(uuid);
		return memberEntity.toDomain();
	}

	@Override
	public void create(Role role) {
		RoleEntity roleEntity = RoleEntity.fromDomain(role);
		roleRepository.save(roleEntity);
	}

	@Override
	public Role findRoleById(int roleID) {
		RoleEntity roleEntity = roleRepository.findById(roleID).orElse(new RoleEntity());
		return roleEntity.toDomain();
	}

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
}
