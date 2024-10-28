package com.promptoven.authservice.adaptor.web.controller;

import com.promptoven.authservice.application.port.in.usecase.MemberUseCases;
import com.promptoven.authservice.adaptor.web.controller.vo.in.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/member")
public class MemberAdminRestController {

	private final MemberUseCases memberManagementUseCases;

	@PostMapping("/admin-register")
	public void adminRegister(@RequestBody RegisterRequestVO registerRequestVO) {
		memberManagementUseCases.AdminRegister(registerRequestVO.getEmail(), registerRequestVO.getPassword(),
			registerRequestVO.getNickname());
	}

	@PutMapping("/ban")
	public void banMember(@RequestBody BanRequestVO banRequestVO) {
		memberManagementUseCases.banMember(banRequestVO.getMemberUUID());
	}

	@PutMapping("/unban")
	public void unbanMember(@RequestBody UnbanRequestVO unbanRequestVO) {
		memberManagementUseCases.unbanMember(unbanRequestVO.getMemberUUID());
	}

	@PutMapping("/nickname")
	public void updateNickname(@RequestBody UpdateNicknameRequestVO updateNicknameRequestVO) {
		memberManagementUseCases.updateNickname(
			updateNicknameRequestVO.getMemberUUID(), updateNicknameRequestVO.getNickname());
	}

	@PutMapping("/member-role")
	public void updateMemberRole(@RequestBody SetMemberRoleRequestVO setMemberRoleRequestVO) {
		memberManagementUseCases.setMemberRole(
			setMemberRoleRequestVO.getMemberNickname(), setMemberRoleRequestVO.getRoleName());
	}

	@PutMapping("/clearPW")
	public void clearPassword(@RequestBody ClearPasswordRequestVO clearPasswordRequestVO) {
		memberManagementUseCases.clearPassword(clearPasswordRequestVO.getMemberUUID());
	}

	@PostMapping("/role")
	public void createRole(@RequestBody CreateRoleRequestVO createRoleRequestVO) {
		memberManagementUseCases.createRole(createRoleRequestVO.getName(), createRoleRequestVO.getDescription());
	}

	@PutMapping("/role")
	public void updateRole(@RequestBody UpdateRoleRequestVO updateRoleRequestVO) {
		memberManagementUseCases.updateRole(updateRoleRequestVO.getId(), updateRoleRequestVO.getName(),
			updateRoleRequestVO.getDescription());
	}

	@DeleteMapping("/role")
	public void deleteRole(@RequestBody DeleteRoleRequestVO deleteRoleRequestVO) {
		memberManagementUseCases.deleteRole(deleteRoleRequestVO.getRoleID());
	}

}
