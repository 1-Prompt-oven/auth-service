package com.promptoven.authservice.adaptor.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.adaptor.web.controller.vo.in.OauthLoginRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.OauthRegisterRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.OauthUnregisterRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.out.OauthInfoResponseVO;
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
		SocialLoginDTO socialLoginDTO = socialLoginUseCase.oauthLogin(oauthLoginRequestVO.toDTO());
		return SocialLoginResponseVO.from(socialLoginDTO);
	}

	@PostMapping("/oauth/register")
	public void oauthRegister(@RequestBody OauthRegisterRequestVO oauthRegisterRequestVO) {
		socialLoginUseCase.OauthRegister(oauthRegisterRequestVO.toDTO());
	}

	@PostMapping("/oauth/unregister")
	public void oauthUnregister(@RequestBody OauthUnregisterRequestVO oauthUnregisterRequestVO) {
		socialLoginUseCase.OauthUnregister(oauthUnregisterRequestVO.toDTO());
	}

	@GetMapping("/oauth/info")
	public List<OauthInfoResponseVO> getOauthInfoes(@RequestHeader("Authorization") String accessToken) {
		return socialLoginUseCase.getOauthInfo(accessToken).stream()
			.map(OauthInfoResponseVO::fromDTO)
			.collect(Collectors.toList());
	}

}
