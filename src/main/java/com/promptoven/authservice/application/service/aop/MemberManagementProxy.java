package com.promptoven.authservice.application.service.aop;

import org.springframework.stereotype.Component;

import com.promptoven.authservice.application.port.in.usecase.MemberManagementUseCase;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MemberManagementProxy {

	private final MemberManagementUseCase memberManagementUseCase;

	protected MemberManagementUseCase getMemberManagementUseCase() {
		return memberManagementUseCase;
	}

	@FindMemberOperation
	public void banMember(String memberUUID) {
		// Method body not needed as aspect will handle the service call
	}

	@FindMemberOperation
	public void unbanMember(String memberUUID) {
	}

	@FindMemberOperation
	public void updateNickname(String memberUUID, String nickname) {
	}

	@FindMemberOperation
	public void setMemberRole(String memberUUID, String roleName) {
	}

	@FindMemberOperation
	public void clearPassword(String memberUUID) {
	}

	@FindMemberOperation
	public void promoteToSeller(String memberUuid) {
	}
} 