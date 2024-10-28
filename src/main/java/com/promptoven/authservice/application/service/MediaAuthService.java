package com.promptoven.authservice.application.service;

import com.promptoven.authservice.application.port.in.usecase.MediaAuthUseCase;
import com.promptoven.authservice.application.port.out.call.AuthTaskMemory;
import com.promptoven.authservice.application.port.out.call.MailSending;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@Service
public class MediaAuthService implements MediaAuthUseCase {

    @Value("${auth.challenge.expiration}")
    private long AUTH_CHALLENGE_EXPIRE_TIME;

    private final AuthTaskMemory authTaskMemory;
    private final MailSending mailSending;

    @Override
	public boolean checkMedia(String email, String code) {
		if (authTaskMemory.getAuthChallenge(email).equals(code)) {
			saveSuccessAuthChallenge(email);
			return true;
		}
		return false;
	}

	protected void saveSuccessAuthChallenge(String email) {
		Date expires = new Date(AUTH_CHALLENGE_EXPIRE_TIME + System.currentTimeMillis());
		authTaskMemory.recordAuthChallengeSuccess(email, expires);
	}

	@Override
	public void requestEmail(String email) {
		Date expires = new Date(AUTH_CHALLENGE_EXPIRE_TIME + System.currentTimeMillis());
		String code = String.format("%06d", new Random().nextInt(1000000));
		mailSending.sendMail(email, "Email Verification Code", "Your verification code is " + code);
		authTaskMemory.recordAuthChallenge(email, code, expires);
	}

    @Override
	public void requestPhone(String phone) {
		Date expires = new Date(AUTH_CHALLENGE_EXPIRE_TIME + System.currentTimeMillis());
		String code = String.format("%06d", new Random().nextInt(1000000));
		// todo: Send SMS or Kakaotalk Alert talk
		authTaskMemory.recordAuthChallenge(phone, code, expires);
    }
}
