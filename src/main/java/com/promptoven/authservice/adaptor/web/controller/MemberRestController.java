package com.promptoven.authservice.adaptor.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.adaptor.web.controller.vo.in.ChangePWRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.UpdateNicknameRequestVO;
import com.promptoven.authservice.application.port.in.usecase.AuthenticationUseCase;
import com.promptoven.authservice.application.port.in.usecase.MemberManagementUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/member/auth")
public class MemberRestController {

	private final MemberManagementUseCase memberUseCases;
	private final AuthenticationUseCase authenticationUseCase;

	@PutMapping("/nickname")
	public void updateNickname(@RequestBody UpdateNicknameRequestVO updateNicknameRequestVO) {
		memberUseCases.updateNickname(updateNicknameRequestVO.getMemberUUID(), updateNicknameRequestVO.getNickname(),
			null);
	}

	@PostMapping("/changePW")
	public void changePW(@RequestBody ChangePWRequestVO changePWRequestVO) {
		authenticationUseCase.changePW(changePWRequestVO.getNewPassword(), changePWRequestVO.getMemberUUID());
	}
}