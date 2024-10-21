package com.promptoven.authservice.application.service.springsecurity.basic;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
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
import com.promptoven.authservice.application.port.out.call.AuthPersistence;
import com.promptoven.authservice.application.port.out.call.AuthRepository;
import com.promptoven.authservice.application.port.out.call.MailService;
import com.promptoven.authservice.application.port.out.call.OauthInfoPersistence;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.port.out.dto.LoginDTO;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;

import lombok.RequiredArgsConstructor;

@Service("authServiceBySpringSecurity")
@RequiredArgsConstructor
public class AuthServiceImpl
	implements ChangePWUseCase, EmailCheckUseCase, EmailRequestUseCase, LoginUseCase,
	OauthLoginUseCase, OauthRegisterUseCase, OauthUnregisterUseCase,
	RegisterFromSocialLoginUseCase, RegisterUseCase, VerifyNicknameUseCase,
	VerifyEmailUseCase {

	private final AuthPersistence authPersistence;
	private final OauthInfoPersistence oauthInfoPersistence;
	private final RolePersistence rolePersistence;
	private final AuthRepository authRepository;
	private final MailService mailService;

	@Value("${auth.challenge.expiration}")
	private long AUTH_CHALLENGE_EXPIRE_TIME;

	@Override
	public void changePW(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		System.out.println("ChangePWUseCase");
	}

	@Override
	public boolean checkMedia(String email, String code) {
		if (authRepository.getAuthChallenge(email).equals(code)) {
			Date expires = new Date(AUTH_CHALLENGE_EXPIRE_TIME + System.currentTimeMillis());
			authRepository.recordAuthChallengeSuccess(email, expires);
			return true;
		}
		return false;
	}

	@Override
	public void requestEmail(String email) {
		Date expires = new Date(AUTH_CHALLENGE_EXPIRE_TIME + System.currentTimeMillis());
		String code = String.format("%06d", new Random().nextInt(1000000));
		try {
			mailService.sendMail(email, "Email Verification Code", "Your verification code is " + code);
			authRepository.recordAuthChallenge(email, code, expires);
		} catch (Exception e) {
			throw e;
		}
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
