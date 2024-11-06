package com.promptoven.authservice.application.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.usecase.MemberManagementUseCase;
import com.promptoven.authservice.application.port.out.call.EventPublisher;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.port.out.dto.MemberNicknameUpdateEvent;
import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.Role;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberManagementService implements MemberManagementUseCase {

	private final MemberPersistence memberPersistence;
	private final RolePersistence rolePersistence;
	private final EventPublisher eventPublisher;

	@Value("${member-ban-event}")
	private String memberBannedTopic;
	@Value("${member-unban-event}")
	private String memberUnbanTopic;
	@Value("${member-nickname-update-event}")
	private String memberNicknameUpdatedTopic;

	public MemberManagementService(MemberPersistence memberPersistence, RolePersistence rolePersistence,
		@Qualifier("eventPublisherByKafka") EventPublisher eventPublisher) {
		this.memberPersistence = memberPersistence;
		this.rolePersistence = rolePersistence;
		this.eventPublisher = eventPublisher;
	}

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
		eventPublisher.publish(memberBannedTopic, member.getUuid());
		memberPersistence.updateMember(Member.banMember(member));
	}

	@Override
	public void unbanMember(Member member) {
		eventPublisher.publish(memberUnbanTopic, member.getUuid());
		memberPersistence.updateMember(Member.unbanMember(member));
	}

	@Override
	public void updateNickname(Member member, String nickname) {
		memberPersistence.updateMember(Member.updateMemberNickname(member, nickname));
		eventPublisher.publish(
			memberNicknameUpdatedTopic,
			new MemberNicknameUpdateEvent(member.getUuid(), nickname)
		);
	}

	@Override
	public void clearPassword(Member member) {
		memberPersistence.updateMember(Member.updateMemberPassword(member, "clear"));
	}
}