package com.promptoven.authservice.adaptor.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.adaptor.web.controller.vo.in.SocialLoginAssociateRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.SocialLoginDisassociateRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.SocialLoginRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.out.OauthInfoResponseVO;
import com.promptoven.authservice.adaptor.web.controller.vo.out.SocialLoginResponseVO;
import com.promptoven.authservice.application.port.in.usecase.SocialLoginUseCase;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth/social")
public class SocialLoginRestController {

	private final SocialLoginUseCase socialLoginUseCase;

	@PostMapping("/login")
	public SocialLoginResponseVO socialLogin(
		@RequestBody SocialLoginRequestVO socialLoginRequestVO) {
		SocialLoginDTO socialLoginDTO = socialLoginUseCase.SocialLogin(socialLoginRequestVO.toDTO());
		return SocialLoginResponseVO.from(socialLoginDTO);
	}

	@PostMapping("/info")
	public void socialLoginAssociate(
		@RequestBody SocialLoginAssociateRequestVO socialLoginAssociateRequestVO) {
		socialLoginUseCase.SocialLoginAssociate(socialLoginAssociateRequestVO.toDTO());
	}

	@DeleteMapping("/info")
	public void socialLoginDisassociate(
		@RequestBody SocialLoginDisassociateRequestVO socialLoginDisassociateRequestVO) {
		socialLoginUseCase.SocialLoginDisassociate(socialLoginDisassociateRequestVO.toDTO());
	}

	@GetMapping("/info")
	public List<OauthInfoResponseVO> getSocialLoginAssociations(
		@RequestHeader("Authorization") String accessToken) {
		return socialLoginUseCase.getSocialLoginAssociations(accessToken).stream()
			.map(OauthInfoResponseVO::fromDTO)
			.collect(Collectors.toList());
	}

}
