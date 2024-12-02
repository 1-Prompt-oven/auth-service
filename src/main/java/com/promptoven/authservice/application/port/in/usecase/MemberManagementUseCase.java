package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.in.dto.ChangePWRequestDTO;
import com.promptoven.authservice.application.port.in.dto.ResetPWRequestDTO;
import com.promptoven.authservice.application.port.in.dto.SetMemberRoleRequestDTO;
import com.promptoven.authservice.application.port.in.dto.UpdateNicknameRequestDTO;
import com.promptoven.authservice.domain.Member;

public interface MemberManagementUseCase {

	void promoteToSeller(Member member);

	void setMemberRole(Member member, SetMemberRoleRequestDTO setMemberRoleRequestDTO);

	void banMember(Member member);

	void unbanMember(Member member);

	void updateNickname(Member member, UpdateNicknameRequestDTO updateNicknameRequestDTO);

	void clearPassword(Member member);

	void changePW(ChangePWRequestDTO changePWRequestDTO);

	void resetPW(ResetPWRequestDTO resetPWRequestDTO);

}
