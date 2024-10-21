package com.promptoven.authservice.adaptor.web.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.application.port.in.usecase.MemberUseCases;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth/member")
public class MemberRestController {

	private final MemberUseCases memberUseCases;

	@PutMapping("/nickname")
	public void updateNickname(String memberUUID, String nickname) {
		memberUseCases.updateNickname(memberUUID, nickname);
	}
}
