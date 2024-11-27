package com.promptoven.authservice.adaptor.web.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.adaptor.web.util.BaseResponse;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.BanRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.ClearPasswordRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.CreateRoleRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.DeleteRoleRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.RegisterRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.SetMemberRoleRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.UnbanRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.UpdateNicknameRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.UpdateRoleRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.response.RoleResponseMapper;
import com.promptoven.authservice.adaptor.web.controller.vo.in.BanRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.ClearPasswordRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.CreateRoleRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.DeleteRoleRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.RegisterRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.SetMemberRoleRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.UnbanRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.UpdateNicknameRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.UpdateRoleRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.out.RoleResponseVO;
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
	public BaseResponse<Void> adminRegister(@RequestBody RegisterRequestVO registerRequestVO) {
		memberRegistrationUseCase.AdminRegister(RegisterRequestMapper.toDTO(registerRequestVO));
		return new BaseResponse<>();
	}

	@PutMapping("/ban")
	public BaseResponse<Void> banMember(@RequestBody BanRequestVO banRequestVO) {
		memberManagementProxy.banMember(BanRequestMapper.toDTO(banRequestVO));
		return new BaseResponse<>();
	}

	@PutMapping("/unban")
	public BaseResponse<Void> unbanMember(@RequestBody UnbanRequestVO unbanRequestVO) {
		memberManagementProxy.unbanMember(UnbanRequestMapper.toDTO(unbanRequestVO));
		return new BaseResponse<>();
	}

	@PutMapping("/nickname")
	public BaseResponse<Void> updateNickname(@RequestBody UpdateNicknameRequestVO updateNicknameRequestVO) {
		memberManagementProxy.updateNickname(UpdateNicknameRequestMapper.toDTO(updateNicknameRequestVO));
		return new BaseResponse<>();
	}

	@PutMapping("/member-role")
	public BaseResponse<Void> updateMemberRole(@RequestBody SetMemberRoleRequestVO setMemberRoleRequestVO) {
		memberManagementProxy.setMemberRole(SetMemberRoleRequestMapper.toDTO(setMemberRoleRequestVO));
		return new BaseResponse<>();
	}

	@PutMapping("/clearPW")
	public BaseResponse<Void> clearPassword(@RequestBody ClearPasswordRequestVO clearPasswordRequestVO) {
		memberManagementProxy.clearPassword(ClearPasswordRequestMapper.toDTO(clearPasswordRequestVO));
		return new BaseResponse<>();
	}

	@PostMapping("/role")
	public BaseResponse<Void> createRole(@RequestBody CreateRoleRequestVO createRoleRequestVO) {
		roleManagementUseCase.createRole(CreateRoleRequestMapper.toDTO(createRoleRequestVO));
		return new BaseResponse<>();
	}

	@PutMapping("/role")
	public BaseResponse<Void> updateRole(@RequestBody UpdateRoleRequestVO updateRoleRequestVO) {
		roleManagementUseCase.updateRole(UpdateRoleRequestMapper.toDTO(updateRoleRequestVO));
		return new BaseResponse<>();
	}

	@DeleteMapping("/role")
	public BaseResponse<Void> deleteRole(@RequestBody DeleteRoleRequestVO deleteRoleRequestVO) {
		roleManagementUseCase.deleteRole(DeleteRoleRequestMapper.toDTO(deleteRoleRequestVO));
		return new BaseResponse<>();
	}

	@GetMapping("/role")
	public BaseResponse<List<RoleResponseVO>> getRole() {
		return new BaseResponse<>(roleManagementUseCase.getRole().stream().map(RoleResponseMapper::fromDTO).toList());
	}

}
