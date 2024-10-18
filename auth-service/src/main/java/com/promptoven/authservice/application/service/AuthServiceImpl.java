package com.promptoven.authservice.application.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.usecase.ChangePWUseCase;
import com.promptoven.authservice.application.port.in.usecase.LoginUseCase;
import com.promptoven.authservice.application.port.in.usecase.MediaCheckUseCase;
import com.promptoven.authservice.application.port.in.usecase.MediaRequestUseCase;
import com.promptoven.authservice.application.port.in.usecase.OauthLoginUseCase;
import com.promptoven.authservice.application.port.in.usecase.OauthRegisterUseCase;
import com.promptoven.authservice.application.port.in.usecase.OauthUnregisterUseCase;
import com.promptoven.authservice.application.port.in.usecase.RegisterFromSocialLoginUseCase;
import com.promptoven.authservice.application.port.in.usecase.RegisterUseCase;
import com.promptoven.authservice.application.port.in.usecase.ResetPWUseCase;
import com.promptoven.authservice.application.port.in.usecase.VerifyEmailUseCase;
import com.promptoven.authservice.application.port.in.usecase.VerifyNicknameUseCase;
import com.promptoven.authservice.application.port.in.usecase.VerifyPhoneUseCase;
import com.promptoven.authservice.application.port.out.call.CallMemberByEmail;
import com.promptoven.authservice.application.port.out.dto.LoginDTO;
import com.promptoven.authservice.application.port.out.dto.MediaCheckDTO;
import com.promptoven.authservice.application.port.out.dto.VerifyDTO;
import com.promptoven.authservice.domain.Member;

/*
 * AuthService : 인증 서비스
 * 1. login : 로그인
 * 2. oauthLogin : OAuth 로그인
 * 3. logout : 로그아웃
 * 4. register : 회원가입
 * 5. oauthRegister : OAuth 연동
 * 6. oauthUnregister : OAuth 연동해제
 * 7. withdraw : 회원탈퇴
 * 8. resetPW : 비밀번호 재설정
 * 9. changePW : 비밀번호 변경
 * 10. emailAuth : 이메일 인증
 * 11. emailAuthCheck : 이메일 인증 확인
 * 12. verifyEmail : 이메일 중복 확인
 * 13. verifyNickname : 닉네임 중복 확인
 * 14. verifyPhone : 전화번호 중복 확인
 * 15. register-social : 소셜 회원가입
 * */

@Service
public class AuthServiceImpl {

	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(13);
	private static final CallMemberByEmail callMemberByEmail;

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

	public LoginDTO oauthLogin(OauthLoginUseCase oauthLoginUseCase) {
		return null;
	}

	public void logout() {

	}

	public LoginDTO register(RegisterUseCase registerUseCase) {
		return null;
	}

	public void oauthRegister(OauthRegisterUseCase oauthRegisterUseCase) {

	}

	public void oauthUnregister(OauthUnregisterUseCase oauthUnregisterUseCase) {

	}

	public void withdraw() {

	}

	public void resetPW(ResetPWUseCase resetPWUseCase) {

	}

	public void changePW(ChangePWUseCase changePWUseCase) {

	}

	public void emailAuth(MediaRequestUseCase mediaRequestUseCase) {

	}

	public MediaCheckDTO emailAuthCheck(MediaCheckUseCase mediaCheckUseCase) {
		return null;
	}

	public VerifyDTO verifyEmail(VerifyEmailUseCase verifyEmailUseCase) {
		return null;
	}

	public VerifyDTO verifyNickname(VerifyNicknameUseCase verifyNicknameUseCase) {
		return null;
	}

	public VerifyDTO verifyPhone(VerifyPhoneUseCase verifyPhoneUseCase) {
		return null;
	}

	public LoginDTO registerSocial(RegisterFromSocialLoginUseCase registerFromSocialLoginUseCase) {
		return null;
	}

}
