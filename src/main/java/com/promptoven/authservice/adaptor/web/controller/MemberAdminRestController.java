package com.promptoven.authservice.adaptor.web.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.adaptor.web.controller.vo.in.BanRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.ClearPasswordRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.CreateRoleRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.DeleteRoleRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.RegisterRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.SetMemberRoleRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.UnbanRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.UpdateNicknameRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.UpdateRoleRequestVO;
import com.promptoven.authservice.application.port.in.usecase.MemberRegistrationUseCase;
import com.promptoven.authservice.application.port.in.usecase.RoleManagementUseCase;
import com.promptoven.authservice.application.service.aop.MemberManagementProxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/member")
public class MemberAdminRestController {

	private final MemberManagementProxy memberManagementProxy;
	private final MemberRegistrationUseCase memberRegistrationUseCase;
	private final RoleManagementUseCase roleManagementUseCase;

	@PostMapping("/admin-register")
	public void adminRegister(@RequestBody RegisterRequestVO registerRequestVO) {
		memberRegistrationUseCase.AdminRegister(registerRequestVO.getEmail(), registerRequestVO.getPassword(),
			registerRequestVO.getNickname());
	}

	@PutMapping("/ban")
	public void banMember(@RequestBody BanRequestVO banRequestVO) {
		memberManagementProxy.banMember(banRequestVO.getMemberUUID());
	}

	@PutMapping("/unban")
	public void unbanMember(@RequestBody UnbanRequestVO unbanRequestVO) {
		memberManagementProxy.unbanMember(unbanRequestVO.getMemberUUID());
	}

	@PutMapping("/nickname")
	public void updateNickname(@RequestBody UpdateNicknameRequestVO updateNicknameRequestVO) {
		memberManagementProxy.updateNickname(
			updateNicknameRequestVO.getMemberUUID(),
			updateNicknameRequestVO.getNickname()
		);
	}

	@PutMapping("/member-role")
	public void updateMemberRole(@RequestBody SetMemberRoleRequestVO setMemberRoleRequestVO) {
		memberManagementProxy.setMemberRole(
			setMemberRoleRequestVO.getMemberNickname(),
			setMemberRoleRequestVO.getRoleName()
		);
	}

	@PutMapping("/clearPW")
	public void clearPassword(@RequestBody ClearPasswordRequestVO clearPasswordRequestVO) {
		memberManagementProxy.clearPassword(clearPasswordRequestVO.getMemberUUID());
	}

	@PostMapping("/role")
	public void createRole(@RequestBody CreateRoleRequestVO createRoleRequestVO) {
		roleManagementUseCase.createRole(createRoleRequestVO.getName(), createRoleRequestVO.getDescription());
	}

	@PutMapping("/role")
	public void updateRole(@RequestBody UpdateRoleRequestVO updateRoleRequestVO) {
		roleManagementUseCase.updateRole(updateRoleRequestVO.getId(), updateRoleRequestVO.getName(),
			updateRoleRequestVO.getDescription());
	}

	@DeleteMapping("/role")
	public void deleteRole(@RequestBody DeleteRoleRequestVO deleteRoleRequestVO) {
		roleManagementUseCase.deleteRole(deleteRoleRequestVO.getRoleID());
	}

}
