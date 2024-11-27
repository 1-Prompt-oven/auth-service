package com.promptoven.authservice.adaptor.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.adaptor.web.util.BaseResponse;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.RegisterRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.RegisterSocialRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.VerifyEmailRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.VerifyNicknameRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.response.LoginResponseMapper;
import com.promptoven.authservice.adaptor.web.controller.vo.in.RegisterRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.RegisterSocialRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.VerifyEmailRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.VerifyNicknameRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.out.LoginResponseVO;
import com.promptoven.authservice.application.port.in.usecase.MemberRegistrationUseCase;
import com.promptoven.authservice.application.port.in.usecase.VerificationUseCase;
import com.promptoven.authservice.application.port.out.dto.LoginResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class MemberRegisterRestController {

	private final MemberRegistrationUseCase memberRegistrationUseCase;
	private final VerificationUseCase verificationUseCase;

	@PostMapping("/register")
	public BaseResponse<LoginResponseVO> register(@RequestBody RegisterRequestVO registerRequestVO) {
		LoginResponseDTO loginResponseDTO = memberRegistrationUseCase.register(
			RegisterRequestMapper.toDTO(registerRequestVO));
		return new BaseResponse<>(LoginResponseMapper.fromDTO(loginResponseDTO));
	}

	@PostMapping("/verify/email")
	public BaseResponse<Boolean> verifyEmail(@RequestBody VerifyEmailRequestVO verifyEmailRequestVO) {
		return new BaseResponse<>(verificationUseCase.verifyEmail(VerifyEmailRequestMapper.toDTO(verifyEmailRequestVO)));
	}

	@PostMapping("/verify/nickname")
	public BaseResponse<Boolean> verifyNickname(@RequestBody VerifyNicknameRequestVO verifyNicknameRequestVO) {
		return new BaseResponse<>(verificationUseCase.verifyNickname(VerifyNicknameRequestMapper.toDTO(verifyNicknameRequestVO)));
	}

	@PostMapping("/register-social")
	public BaseResponse<LoginResponseVO> registerSocial(@RequestBody RegisterSocialRequestVO registerSocialRequestVO) {
		return new BaseResponse<>(LoginResponseMapper.fromDTO(memberRegistrationUseCase.registerFromSocialLogin(
			RegisterSocialRequestMapper.toDTO(registerSocialRequestVO))));
	}

}
