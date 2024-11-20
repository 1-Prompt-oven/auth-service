package com.promptoven.authservice.adaptor.jpa;

import org.springframework.stereotype.Service;

import com.promptoven.authservice.adaptor.jpa.entity.MemberEntity;
import com.promptoven.authservice.adaptor.jpa.repository.MemberRepository;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.service.dto.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberPersistenceWithJpa implements MemberPersistence {

	private final MemberRepository memberRepository;

	@Override
	public void create(MemberDTO memberDTO) {
		MemberEntity memberEntity = JpaMemberDTOEntityMapper.toEntity(memberDTO);
		memberRepository.save(memberEntity);
	}

	@Override
	public MemberDTO findByEmail(String email) {
		MemberEntity memberEntity = memberRepository.findByEmail(email);
		return null != memberEntity ? JpaMemberDTOEntityMapper.toDTO(memberEntity) : null;
	}

	@Override
	public MemberDTO findByUuid(String uuid) {
		MemberEntity memberEntity = memberRepository.findByUuid(uuid);
		return null != memberEntity ? JpaMemberDTOEntityMapper.toDTO(memberEntity) : null;
	}

	@Override
	public MemberDTO findByNickname(String nickname) {
		MemberEntity memberEntity = memberRepository.findByNickname(nickname);
		return null != memberEntity ? JpaMemberDTOEntityMapper.toDTO(memberEntity) : null;
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
	public void updatePassword(MemberDTO updatedMember) {
		MemberEntity memberEntity = JpaMemberDTOEntityMapper.toEntity(updatedMember);
		memberEntity.setId(memberRepository.findByUuid(updatedMember.getUuid()).getId());
		memberRepository.save(memberEntity);
	}

	@Override
	public void updateMember(MemberDTO updatedMember) {
		MemberEntity memberEntity = JpaMemberDTOEntityMapper.toEntity(updatedMember);
		memberEntity.setId(memberRepository.findByUuid(updatedMember.getUuid()).getId());
		memberRepository.save(memberEntity);
	}

	@Override
	public void remove(MemberDTO member) {
		MemberEntity memberEntity = JpaMemberDTOEntityMapper.toEntity(member);
		memberEntity.setId(memberRepository.findByUuid(member.getUuid()).getId());
		memberRepository.save(memberEntity);
	}
}
