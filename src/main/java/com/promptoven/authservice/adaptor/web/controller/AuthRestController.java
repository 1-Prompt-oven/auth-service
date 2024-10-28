package com.promptoven.authservice.adaptor.web.controller;

import com.promptoven.authservice.application.port.in.usecase.AuthenticationUseCase;
import com.promptoven.authservice.application.port.in.usecase.MediaAuthUseCase;
import com.promptoven.authservice.application.port.in.usecase.MemberRegistrationUseCase;
import com.promptoven.authservice.application.port.in.usecase.SocialLoginUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.adaptor.web.controller.vo.in.ChangePWRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.CheckPWRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.EmailCheckRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.EmailRequestRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.LoginRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.OauthLoginRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.OauthRegisterRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.OauthUnregisterRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.RegisterRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.RegisterSocialRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.ResetPWRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.VerifyEmailRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.VerifyNicknameRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.out.LoginResponseVO;
import com.promptoven.authservice.adaptor.web.controller.vo.out.SocialLoginResponseVO;
import com.promptoven.authservice.application.port.out.dto.LoginDTO;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthRestController {

	private final AuthenticationUseCase authenticationUseCase;
	private final MediaAuthUseCase mediaAuthUseCase;
	private final MemberRegistrationUseCase memberRegistrationUseCase;
	private final SocialLoginUseCase socialLoginUseCase;

	@PostMapping("/login")
	public LoginResponseVO login(@RequestBody LoginRequestVO loginRequestVO) {
		LoginDTO loginDTO = authenticationUseCase.login(loginRequestVO.getEmail(), loginRequestVO.getPassword());
		return LoginResponseVO.from(loginDTO);
	}

	@PostMapping("/oauth/login")
	public SocialLoginResponseVO oauthLogin(@RequestBody OauthLoginRequestVO oauthLoginRequestVO) {
		SocialLoginDTO socialLoginDTO = socialLoginUseCase.oauthLogin(
			oauthLoginRequestVO.getProvider(), oauthLoginRequestVO.getProviderID(), oauthLoginRequestVO.getEmail());
		return SocialLoginResponseVO.from(socialLoginDTO);
		// todo: 만약에 false 들어가있고 나머지가 들어가 null 이면 register-social 가야합니다. @Frontend Dev : 302 redirect
	}

	@PostMapping("/logout")
	public void logout(@RequestHeader("Authorization") String accessToken,
		@RequestHeader("RefreshToken") String refreshToken) {
		authenticationUseCase.logout(accessToken, refreshToken);
	}

	@PostMapping("/register")
	public LoginResponseVO register(@RequestBody RegisterRequestVO registerRequestVO) {
		LoginDTO loginDTO = memberRegistrationUseCase.register(registerRequestVO.getEmail(), registerRequestVO.getPassword(),
			registerRequestVO.getNickname());
		return LoginResponseVO.from(loginDTO);
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

	@PostMapping("/withdraw")
	public void withdraw(@RequestHeader("Authorization") String authorizationHeader) {
		String accessToken = authorizationHeader.replace("Bearer ", "");
		authenticationUseCase.withdraw(accessToken);
	}

	@PostMapping("/resetPW")
	public void resetPW(@RequestBody ResetPWRequestVO resetPWRequestVO) {
		authenticationUseCase.resetPW(resetPWRequestVO.getEmail(), resetPWRequestVO.getPassword());
	}

	@PostMapping("/changePW")
	public void changePW(@RequestBody ChangePWRequestVO changePWRequestVO) {
		authenticationUseCase.changePW(changePWRequestVO.getNewPassword(), changePWRequestVO.getMemberUUID());
	}

	@PostMapping("/checkPW")
	public boolean checkPW(@RequestBody CheckPWRequestVO checkPWRequestVO) {
		return authenticationUseCase.checkPW(checkPWRequestVO.getPassword(), checkPWRequestVO.getMemberUUID());
	}

	@PostMapping("/email/request")
	public void emailRequest(@RequestBody EmailRequestRequestVO emailRequestRequestVO) {
		log.info("email request: {}", emailRequestRequestVO);
		mediaAuthUseCase.requestEmail(emailRequestRequestVO.getEmail());
	}

	@PostMapping("/email/check")
	public boolean emailCheck(@RequestBody EmailCheckRequestVO emailCheckRequestVO) {
		log.info("email check: {}", emailCheckRequestVO);
		return mediaAuthUseCase.checkMedia(emailCheckRequestVO.getEmail(), emailCheckRequestVO.getCode());
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
		));

	}

	@GetMapping("/refresh")
	public String tokenUpdate(@RequestHeader("RefreshToken") String refreshToken) {
		return authenticationUseCase.refresh(refreshToken);
	}

}
