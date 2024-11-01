package com.promptoven.authservice.application.service;

import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.usecase.MemberManagementUseCase;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.service.annotation.FindMemberOperation;
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

	@Override
	@FindMemberOperation
	public void promoteToSeller(String memberUUID, Member member) {
		memberPersistence.updateMember(Member.updateMemberRole(member, 2));
	}

	@Override
	@FindMemberOperation
	public void setMemberRole(String memberUUID, String roleName, Member member) {
		// The aspect will convert the UUID to a Member object
		Role role = rolePersistence.findByName(roleName);
		memberPersistence.updateMember(Member.updateMemberRole(member, role.getId()));
	}

	@Override
	@FindMemberOperation
	public void banMember(String memberUUID, Member member) {
		// The aspect will convert the UUID to a Member object
		memberPersistence.updateMember(Member.banMember(member));
	}

	@Override
	@FindMemberOperation
	public void unbanMember(String memberUUID, Member member) {
		// The aspect will convert the UUID to a Member object
		memberPersistence.updateMember(Member.unbanMember(member));
	}

	@Override
	@FindMemberOperation
	public void updateNickname(String memberUUID, String nickname, Member member) {
		// The aspect will convert the UUID to a Member object
		memberPersistence.updateMember(Member.updateMemberNickname(member, nickname));
	}

	@Override
	@FindMemberOperation
	public void clearPassword(String memberUUID, Member member) {
		// The aspect will convert the UUID to a Member object
		memberPersistence.updateMember(Member.updateMemberPassword(member, "clear"));
	}
}