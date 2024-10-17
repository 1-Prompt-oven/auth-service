package com.promptoven.authservice.adaptor.infrastructure.jpa;

import org.springframework.stereotype.Service;

import com.promptoven.authservice.adaptor.infrastructure.AuthPersistence;
import com.promptoven.authservice.adaptor.infrastructure.jpa.entity.MemberEntity;
import com.promptoven.authservice.adaptor.infrastructure.jpa.repository.MemberRepository;
import com.promptoven.authservice.domain.Member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthPersistenceImplByJpa implements AuthPersistence {

	private final MemberRepository memberRepository;

	@Override
	public void create(Member member) {
		MemberEntity memberEntity = new MemberEntity(member);
		memberRepository.save(memberEntity);
	}

	@Override
	public Member findByEmail(String email) {
		MemberEntity memberEntity = memberRepository.findByEmail(email);
		return memberEntity.toDomain();
	}

	@Override
	public Member findByUuid(String uuid) {
		return null;
	}

}
