package com.promptoven.authservice.adaptor.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.adaptor.web.controller.vo.in.ChangePWRequestVO;
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
import com.promptoven.authservice.application.port.in.usecase.ChangePWUseCase;
import com.promptoven.authservice.application.port.in.usecase.LoginUseCase;
import com.promptoven.authservice.application.port.out.dto.LoginDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthRestController {

	private final ChangePWUseCase changePWUseCase;
	private final LoginUseCase loginUseCase;

	@PostMapping("/login")
	public LoginResponseVO login(@RequestBody LoginRequestVO loginRequestVO) {
		LoginDTO loginDTO = loginUseCase.login(loginRequestVO.getEmail(), loginRequestVO.getPassword());
		return LoginResponseVO.from(loginDTO);
	}

	@PostMapping("/oauth/login")
	public LoginResponseVO oauthLogin(@RequestBody OauthLoginRequestVO oauthLoginRequestVO) {
		return new LoginResponseVO();
	}

	@PostMapping("/logout")
	public void logout(@RequestHeader("Authorization") String authorizationHeader) {
		String accessToken = authorizationHeader.replace("Bearer ", "");
		log.info("logout token: {}", accessToken);
	}

	@PostMapping("/register")
	public LoginResponseVO register(@RequestBody RegisterRequestVO registerRequestVO) {
		log.info("register: {}", registerRequestVO);
		return new LoginResponseVO();
	}

	@PostMapping("/oauth/register")
	public void oauthRegister(@RequestBody OauthRegisterRequestVO oauthRegisterRequestVO) {
		log.info("oauth register: {}", oauthRegisterRequestVO);
	}

	@PostMapping("/oauth/unregister")
	public void oauthUnregister(@RequestBody OauthUnregisterRequestVO oauthUnregisterRequestVO) {
		log.info("oauth unregister: {}", oauthUnregisterRequestVO);
	}

	@PostMapping("/withdraw")
	public void withdraw(@RequestHeader("Authorization") String authorizationHeader) {
		String accessToken = authorizationHeader.replace("Bearer ", "");
		log.info("withdraw token: {}", accessToken);
	}

	@PostMapping("/resetPW")
	public void resetPW(@RequestBody ResetPWRequestVO resetPWRequestVO) {
		log.info("reset password: {}", resetPWRequestVO);
	}

	@PostMapping("/changePW")
	public void changePW(@RequestBody ChangePWRequestVO changePWRequestVO) {
		changePWUseCase.changePW(changePWRequestVO.getPassword(), changePWRequestVO.getNewPassword(),
			changePWRequestVO.getMemberUUID());
		log.info("change password: {}", changePWRequestVO);
	}

	@PostMapping("/email/reqeust")
	public void emailRequest(@RequestBody EmailRequestRequestVO emailRequestRequestVO) {
		log.info("email request: {}", emailRequestRequestVO);
	}

	@PostMapping("/email/check")
	public boolean emailCheck(@RequestBody EmailCheckRequestVO emailCheckRequestVO) {
		log.info("email check: {}", emailCheckRequestVO);
		return true;
	}

	@PostMapping("/verify/email")
	public boolean verifyEmail(@RequestBody VerifyEmailRequestVO verifyEmailRequestVO) {
		log.info("verify email: {}", verifyEmailRequestVO);
		return true;
	}

	@PostMapping("/verify/nickname")
	public boolean verifyNickname(@RequestBody VerifyNicknameRequestVO verifyNicknameRequestVO) {
		log.info("verify nickname: {}", verifyNicknameRequestVO);
		return true;
	}

	@PostMapping("/register-social")
	public LoginResponseVO registerSocial(@RequestBody RegisterSocialRequestVO registerSocialRequestVO) {
		log.info("register social: {}", registerSocialRequestVO);
		return new LoginResponseVO();
	}
}
