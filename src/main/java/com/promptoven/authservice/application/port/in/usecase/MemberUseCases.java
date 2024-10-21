package com.promptoven.authservice.application.port.in.usecase;

public interface MemberUseCases {

	void promoteToSeller(String memberUUID);

	void setMemberRole(String memberUUID, String roleName);

	void banMember(String memberUUID);

	void unbanMember(String memberUUID);

	void updateNickname(String memberUUID, String nickname);
}
