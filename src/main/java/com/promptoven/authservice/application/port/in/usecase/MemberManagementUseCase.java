package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.domain.Member;

public interface MemberManagementUseCase {

	void promoteToSeller(Member member);

	void setMemberRole(Member member, String roleName);

	void banMember(Member member);

	void unbanMember(Member member);

	void updateNickname(Member member, String nickname);

	void clearPassword(Member member);
}
