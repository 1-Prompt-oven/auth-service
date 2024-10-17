package com.promptoven.authservice.application.service;

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
import com.promptoven.authservice.application.port.out.LoginDTO;
import com.promptoven.authservice.application.port.out.MediaCheckDTO;
import com.promptoven.authservice.application.port.out.VerifyDTO;

public interface AuthService {

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

	// 로그인
	LoginDTO login(LoginUseCase loginUseCase);

	// OAuth 로그인
	LoginDTO oauthLogin(OauthLoginUseCase oauthLoginUseCase);

	// 로그아웃
	void logout();

	// 회원가입
	LoginDTO register(RegisterUseCase registerUseCase);

	// OAuth 연동
	void oauthRegister(OauthRegisterUseCase oauthRegisterUseCase);

	// OAuth 연동해제
	void oauthUnregister(OauthUnregisterUseCase oauthUnregisterUseCase);

	// 회원탈퇴
	void withdraw();

	// 비밀번호 재설정
	void resetPW(ResetPWUseCase resetPWUseCase);

	// 비밀번호 변경
	void changePW(ChangePWUseCase changePWUseCase);

	// 이메일 인증
	void emailAuth(MediaRequestUseCase mediaRequestUseCase);

	// 이메일 인증 확인
	MediaCheckDTO emailAuthCheck(MediaCheckUseCase mediaCheckUseCase);

	// 이메일 중복 확인
	VerifyDTO verifyEmail(VerifyEmailUseCase verifyEmailUseCase);

	// 닉네임 중복 확인
	VerifyDTO verifyNickname(VerifyNicknameUseCase verifyNicknameUseCase);

	// 전화번호 중복 확인
	VerifyDTO verifyPhone(VerifyPhoneUseCase verifyPhoneUseCase);

	// 소셜 회원가입
	LoginDTO registerSocial(RegisterFromSocialLoginUseCase registerFromSocialLoginUseCase);

}
