package com.promptoven.authservice.adaptor.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.adaptor.web.controller.vo.in.OauthLoginRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.OauthRegisterRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.OauthUnregisterRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.out.SocialLoginResponseVO;
import com.promptoven.authservice.application.port.in.usecase.SocialLoginUseCase;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class SocialLoginRestController {

	private final SocialLoginUseCase socialLoginUseCase;

	@PostMapping("/oauth/login")
	public SocialLoginResponseVO oauthLogin(@RequestBody OauthLoginRequestVO oauthLoginRequestVO) {
		SocialLoginDTO socialLoginDTO = socialLoginUseCase.oauthLogin(
			oauthLoginRequestVO.getProvider(), oauthLoginRequestVO.getProviderID(), oauthLoginRequestVO.getEmail());
		return SocialLoginResponseVO.from(socialLoginDTO);
		// todo: 만약에 false 들어가있고 나머지가 들어가 null 이면 register-social 가야합니다. @Frontend Dev : 302 redirect
	}

	@PostMapping("/oauth/register")
	public void oauthRegister(@RequestBody OauthRegisterRequestVO oauthRegisterRequestVO) {
		socialLoginUseCase.OauthRegister(oauthRegisterRequestVO.getProvider(), oauthRegisterRequestVO.getProviderId(),
			oauthRegisterRequestVO.getMemberUUID());
	}

	@PostMapping("/oauth/unregister")
	public void oauthUnregister(@RequestBody OauthUnregisterRequestVO oauthUnregisterRequestVO) {
		socialLoginUseCase.OauthUnregister(oauthUnregisterRequestVO.getProvider(),
			oauthUnregisterRequestVO.getProviderId(), oauthUnregisterRequestVO.getMemberUUID());
	}

}
