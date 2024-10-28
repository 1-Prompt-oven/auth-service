package com.promptoven.authservice.adaptor.web.controller;

import com.promptoven.authservice.adaptor.web.controller.vo.in.UpdateNicknameRequestVO;
import com.promptoven.authservice.application.port.in.usecase.MemberManagementUseCase;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/member/auth")
public class MemberRestController {

	private final MemberManagementUseCase memberUseCases;

	@PutMapping("/nickname")
	public void updateNickname(@RequestBody UpdateNicknameRequestVO updateNicknameRequestVO) {
		memberUseCases.updateNickname(updateNicknameRequestVO.getMemberUUID(), updateNicknameRequestVO.getNickname());
	}
}
