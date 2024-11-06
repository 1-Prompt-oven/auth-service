package com.promptoven.authservice.application.service;

import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.usecase.MemberManagementUseCase;
import com.promptoven.authservice.application.port.out.call.EventPublisher;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.port.out.dto.MemberNicknameUpdateEvent;
import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberManagementService implements MemberManagementUseCase {

	private final MemberPersistence memberPersistence;
	private final RolePersistence rolePersistence;
	private final EventPublisher eventPublisher;

	@Override
	public void promoteToSeller(Member member) {
		memberPersistence.updateMember(Member.updateMemberRole(member, 2));
	}

	@Override
	public void setMemberRole(Member member, String roleName) {
		Role role = rolePersistence.findByName(roleName);
		memberPersistence.updateMember(Member.updateMemberRole(member, role.getId()));
	}

	@Override
	public void banMember(Member member) {
		eventPublisher.publish("member-banned", member.getUuid());
		memberPersistence.updateMember(Member.banMember(member));
	}

	@Override
	public void unbanMember(Member member) {
		eventPublisher.publish("member-unbanned", member.getUuid());
		memberPersistence.updateMember(Member.unbanMember(member));
	}

	@Override
	public void updateNickname(Member member, String nickname) {
		memberPersistence.updateMember(Member.updateMemberNickname(member, nickname));
		MemberNicknameUpdateEvent memberNicknameUpdateEvent = new MemberNicknameUpdateEvent(member.getUuid(), nickname);
		eventPublisher.publish("member-nickname-updated", memberNicknameUpdateEvent);
	}

	@Override
	public void clearPassword(Member member) {
		memberPersistence.updateMember(Member.updateMemberPassword(member, "clear"));
	}
}