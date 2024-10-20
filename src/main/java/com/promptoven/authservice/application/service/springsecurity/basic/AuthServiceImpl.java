package com.promptoven.authservice.application.service.springsecurity.basic;

import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.usecase.ChangePWUseCase;
import com.promptoven.authservice.application.port.in.usecase.EmailCheckUseCase;
import com.promptoven.authservice.application.port.in.usecase.EmailRequestUseCase;
import com.promptoven.authservice.application.port.in.usecase.LoginUseCase;
import com.promptoven.authservice.application.port.in.usecase.OauthLoginUseCase;
import com.promptoven.authservice.application.port.in.usecase.OauthRegisterUseCase;
import com.promptoven.authservice.application.port.in.usecase.OauthUnregisterUseCase;
import com.promptoven.authservice.application.port.in.usecase.RegisterFromSocialLoginUseCase;
import com.promptoven.authservice.application.port.in.usecase.RegisterUseCase;
import com.promptoven.authservice.application.port.in.usecase.VerifyEmailUseCase;
import com.promptoven.authservice.application.port.in.usecase.VerifyNicknameUseCase;
import com.promptoven.authservice.application.port.out.dto.LoginDTO;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;

@Service("authServiceBySpringSecurity")
public class AuthServiceImpl
	implements ChangePWUseCase, EmailCheckUseCase, EmailRequestUseCase, LoginUseCase,
	OauthLoginUseCase, OauthRegisterUseCase, OauthUnregisterUseCase,
	RegisterFromSocialLoginUseCase, RegisterUseCase, VerifyNicknameUseCase,
	VerifyEmailUseCase {

	@Override
	public void changePW(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		System.out.println("ChangePWUseCase");
	}

	@Override
	public boolean checkMedia(String email, String code) {
		return false;
	}

	@Override
	public void requestEmail(String email) {

	}

	@Override
	public LoginDTO login(String email, String password) {
		return null;
	}

	@Override
	public SocialLoginDTO oauthLogin(String provider, String providerID) {
		return null;
	}

	@Override
	public void register(String provider, String providerID) {

	}

	@Override
	public void unregister(String provider, String providerID) {

	}

	@Override
	public LoginDTO registerFromSocialLogin(String email, String nickname, String password, String provider,
		String providerID) {
		return null;
	}

	@Override
	public LoginDTO register(String email, String password, String nickname) {
		return null;
	}

	@Override
	public boolean verifyNickname(String nickname) {
		return false;
	}

	@Override
	public boolean verifyEmail(String email) {
		return false;
	}
}
