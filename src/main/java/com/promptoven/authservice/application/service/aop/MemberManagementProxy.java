package com.promptoven.authservice.application.service.aop;

import org.springframework.stereotype.Component;

import com.promptoven.authservice.application.port.in.dto.BanRequestDTO;
import com.promptoven.authservice.application.port.in.dto.ChangePWRequestDTO;
import com.promptoven.authservice.application.port.in.dto.ClearPasswordRequestDTO;
import com.promptoven.authservice.application.port.in.dto.PromoteToSellerRequestDTO;
import com.promptoven.authservice.application.port.in.dto.ResetPWRequestDTO;
import com.promptoven.authservice.application.port.in.dto.SetMemberRoleRequestDTO;
import com.promptoven.authservice.application.port.in.dto.UnbanRequestDTO;
import com.promptoven.authservice.application.port.in.dto.UpdateNicknameRequestDTO;
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
	public void banMember(BanRequestDTO dto) {
		// Method body not needed as aspect will handle the service call
	}

	@FindMemberOperation
	public void unbanMember(UnbanRequestDTO dto) {
	}

	@FindMemberOperation
	public void updateNickname(UpdateNicknameRequestDTO dto) {
	}

	@FindMemberOperation
	public void setMemberRole(SetMemberRoleRequestDTO dto) {
	}

	@FindMemberOperation
	public void clearPassword(ClearPasswordRequestDTO dto) {
	}

	@FindMemberOperation
	public void promoteToSeller(PromoteToSellerRequestDTO dto) {
	}

	@FindMemberOperation
	public void changePW(ChangePWRequestDTO dto) {
	}

	@FindMemberOperation
	public void resetPW(ResetPWRequestDTO dto) {
	}
} 