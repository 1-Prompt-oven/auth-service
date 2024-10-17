package com.promptoven.authservice.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.ChangePWUseCase;
import com.promptoven.authservice.application.port.in.LoginUseCase;
import com.promptoven.authservice.application.port.in.MediaCheckUseCase;
import com.promptoven.authservice.application.port.in.MediaRequestUseCase;
import com.promptoven.authservice.application.port.in.OauthLoginUseCase;
import com.promptoven.authservice.application.port.in.OauthRegisterUseCase;
import com.promptoven.authservice.application.port.in.OauthUnregisterUseCase;
import com.promptoven.authservice.application.port.in.RegisterFromSocialLoginUseCase;
import com.promptoven.authservice.application.port.in.RegisterUseCase;
import com.promptoven.authservice.application.port.in.ResetPWUseCase;
import com.promptoven.authservice.application.port.in.VerifyEmailUseCase;
import com.promptoven.authservice.application.port.in.VerifyNicknameUseCase;
import com.promptoven.authservice.application.port.in.VerifyPhoneUseCase;
import com.promptoven.authservice.application.port.out.call.CallMemberByEmail;
import com.promptoven.authservice.application.port.out.dto.LoginDTO;
import com.promptoven.authservice.application.port.out.dto.MediaCheckDTO;
import com.promptoven.authservice.application.port.out.dto.VerifyDTO;
import com.promptoven.authservice.domain.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(13);
	private static final CallMemberByEmail callMemberByEmail;

	@Override
	public LoginDTO login(LoginUseCase loginUseCase) {
		String email = loginUseCase.getEmail();
		String password = loginUseCase.getPassword();
		Member member = .getMember(email);
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
