package com.promptoven.authservice.adaptor.infrastructure.jpa;

import org.springframework.stereotype.Service;

import com.promptoven.authservice.adaptor.infrastructure.jpa.entity.MemberEntity;
import com.promptoven.authservice.adaptor.infrastructure.jpa.repository.MemberRepository;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberPersistenceWithJpa implements MemberPersistence {

	private final MemberRepository memberRepository;

	@Override
	public void create(Member member) {
		MemberEntity memberEntity = MemberEntity.fromDomain(member);
		memberRepository.save(memberEntity);
	}

	@Override
	public Member findByEmail(String email) {
		MemberEntity memberEntity = memberRepository.findByEmail(email);
		return memberEntity != null ? memberEntity.toDomain() : null;
	}

	@Override
	public Member findByUuid(String uuid) {
		MemberEntity memberEntity = memberRepository.findByUuid(uuid);
		return memberEntity != null ? memberEntity.toDomain() : null;
	}

	@Override
	public Member findByNickname(String nickname) {
		MemberEntity memberEntity = memberRepository.findByNickname(nickname);
		return memberEntity != null ? memberEntity.toDomain() : null;
	}

	@Override
	public boolean existsByEmail(String email) {
		return memberRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByNickname(String nickname) {
		return memberRepository.existsByNickname(nickname);
	}

	@Override
	public void updatePassword(Member updatedMember) {
		MemberEntity memberEntity = MemberEntity.fromDomain(updatedMember);
		memberEntity.setId(memberRepository.findByUuid(updatedMember.getUuid()).getId());
		memberRepository.save(memberEntity);
	}

	@Override
	public void updateMember(Member updatedMember) {
		MemberEntity memberEntity = MemberEntity.fromDomain(updatedMember);
		memberEntity.setId(memberRepository.findByUuid(updatedMember.getUuid()).getId());
		memberRepository.save(memberEntity);
	}

	@Override
	public void remove(Member member) {
		MemberEntity memberEntity = MemberEntity.fromDomain(member);
		memberEntity.setId(memberRepository.findByUuid(member.getUuid()).getId());
		memberRepository.save(memberEntity);
	}
}
