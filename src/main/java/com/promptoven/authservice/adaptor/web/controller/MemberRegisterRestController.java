package com.promptoven.authservice.adaptor.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.adaptor.web.controller.vo.in.RegisterRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.RegisterSocialRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.VerifyEmailRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.VerifyNicknameRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.out.LoginResponseVO;
import com.promptoven.authservice.application.port.in.usecase.MemberRegistrationUseCase;
import com.promptoven.authservice.application.port.out.dto.LoginDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class MemberRegisterRestController {

	private final MemberRegistrationUseCase memberRegistrationUseCase;

	@PostMapping("/register")
	public LoginResponseVO register(@RequestBody RegisterRequestVO registerRequestVO) {
		LoginDTO loginDTO = memberRegistrationUseCase.register(registerRequestVO.getEmail(),
			registerRequestVO.getPassword(),
			registerRequestVO.getNickname());
		return LoginResponseVO.from(loginDTO);
	}

	@PostMapping("/verify/email")
	public boolean verifyEmail(@RequestBody VerifyEmailRequestVO verifyEmailRequestVO) {
		return memberRegistrationUseCase.verifyEmail(verifyEmailRequestVO.getEmail());
	}

	@PostMapping("/verify/nickname")
	public boolean verifyNickname(@RequestBody VerifyNicknameRequestVO verifyNicknameRequestVO) {
		return memberRegistrationUseCase.verifyNickname(verifyNicknameRequestVO.getNickname());
	}

	@PostMapping("/register-social")
	public LoginResponseVO registerSocial(@RequestBody RegisterSocialRequestVO registerSocialRequestVO) {
		return LoginResponseVO.from(memberRegistrationUseCase.registerFromSocialLogin(
				registerSocialRequestVO.getEmail(),
				registerSocialRequestVO.getNickname(),
				registerSocialRequestVO.getPassword(),
				registerSocialRequestVO.getProvider(),
				registerSocialRequestVO.getProviderId()
			)
		);

	}

}
