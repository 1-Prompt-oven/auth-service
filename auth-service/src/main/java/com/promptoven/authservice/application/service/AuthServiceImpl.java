package com.promptoven.authservice.application.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.out.dto.LoginDTO;
import com.promptoven.authservice.application.port.out.dto.MediaCheckDTO;
import com.promptoven.authservice.application.port.out.dto.VerifyDTO;
import com.promptoven.authservice.application.port.out.outputport.CallMemberByEmail;
import com.promptoven.authservice.application.usecase.ChangePWUseCase;
import com.promptoven.authservice.application.usecase.LoginUseCase;
import com.promptoven.authservice.application.usecase.MediaCheckUseCase;
import com.promptoven.authservice.application.usecase.MediaRequestUseCase;
import com.promptoven.authservice.application.usecase.OauthLoginUseCase;
import com.promptoven.authservice.application.usecase.OauthRegisterUseCase;
import com.promptoven.authservice.application.usecase.OauthUnregisterUseCase;
import com.promptoven.authservice.application.usecase.RegisterFromSocialLoginUseCase;
import com.promptoven.authservice.application.usecase.RegisterUseCase;
import com.promptoven.authservice.application.usecase.ResetPWUseCase;
import com.promptoven.authservice.application.usecase.VerifyEmailUseCase;
import com.promptoven.authservice.application.usecase.VerifyNicknameUseCase;
import com.promptoven.authservice.application.usecase.VerifyPhoneUseCase;
import com.promptoven.authservice.domain.Member;

@Service
public class AuthServiceImpl implements AuthService {

	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(13);
	private static final CallMemberByEmail callMemberByEmail;

	@Override
	public LoginDTO login(LoginUseCase loginUseCase) {
		String email = loginUseCase.getEmail();
		String password = loginUseCase.getPassword();
		Member member = callMemberByEmail.getMember(email);
		if (member != null) {
			if (passwordEncoder.matches(password, member.getPassword())) {
				return new LoginDTO();
			}
		}
		return null;
	}

	@Override
	public LoginDTO oauthLogin(OauthLoginUseCase oauthLoginUseCase) {
		return null;
	}

	@Override
	public void logout() {

	}

	@Override
	public LoginDTO register(RegisterUseCase registerUseCase) {
		return null;
	}

	@Override
	public void oauthRegister(OauthRegisterUseCase oauthRegisterUseCase) {

	}

	@Override
	public void oauthUnregister(OauthUnregisterUseCase oauthUnregisterUseCase) {

	}

	@Override
	public void withdraw() {

	}

	@Override
	public void resetPW(ResetPWUseCase resetPWUseCase) {

	}

	@Override
	public void changePW(ChangePWUseCase changePWUseCase) {

	}

	@Override
	public void emailAuth(MediaRequestUseCase mediaRequestUseCase) {

	}

	@Override
	public MediaCheckDTO emailAuthCheck(MediaCheckUseCase mediaCheckUseCase) {
		return null;
	}

	@Override
	public VerifyDTO verifyEmail(VerifyEmailUseCase verifyEmailUseCase) {
		return null;
	}

	@Override
	public VerifyDTO verifyNickname(VerifyNicknameUseCase verifyNicknameUseCase) {
		return null;
	}

	@Override
	public VerifyDTO verifyPhone(VerifyPhoneUseCase verifyPhoneUseCase) {
		return null;
	}

	@Override
	public LoginDTO registerSocial(RegisterFromSocialLoginUseCase registerFromSocialLoginUseCase) {
		return null;
	}

}
