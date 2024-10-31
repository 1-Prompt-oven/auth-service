package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.service.annotation.FindMemberOperation;
import com.promptoven.authservice.domain.Member;

public interface MemberManagementUseCase {

	@FindMemberOperation
	void promoteToSeller(String memberUUID, Member member);

	@FindMemberOperation
	void setMemberRole(String memberUUID, String roleName, Member member);

	@FindMemberOperation
	void banMember(String memberUUID, Member member);

	@FindMemberOperation
	void unbanMember(String memberUUID, Member member);

	@FindMemberOperation
	void updateNickname(String memberUUID, String nickname, Member member);

	@FindMemberOperation
	void clearPassword(String memberUUID, Member member);
}
