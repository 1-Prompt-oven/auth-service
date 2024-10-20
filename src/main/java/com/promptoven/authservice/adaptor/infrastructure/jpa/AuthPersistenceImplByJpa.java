package com.promptoven.authservice.adaptor.infrastructure.jpa;

import org.springframework.stereotype.Component;

import com.promptoven.authservice.adaptor.infrastructure.jpa.entity.MemberEntity;
import com.promptoven.authservice.adaptor.infrastructure.jpa.repository.MemberRepository;
import com.promptoven.authservice.application.port.out.call.AuthPersistence;
import com.promptoven.authservice.domain.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component("adapterAuthPersistenceWithJpa")
public class AuthPersistenceImplByJpa implements AuthPersistence {

	private final MemberRepository memberRepository;

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

}
