package com.promptoven.authservice.application.service;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.dto.EmailCheckRequestDTO;
import com.promptoven.authservice.application.port.in.dto.EmailRequestRequestDTO;
import com.promptoven.authservice.application.port.in.usecase.MediaAuthUseCase;
import com.promptoven.authservice.application.port.out.call.AuthTaskMemory;
import com.promptoven.authservice.application.port.out.call.MailSending;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MediaAuthService implements MediaAuthUseCase {

	private final AuthTaskMemory authTaskMemory;
	private final MailSending mailSending;
	@Value("${auth.challenge.expiration}")
	private long AUTH_CHALLENGE_EXPIRE_TIME;

	@Override
	public boolean checkMedia(EmailCheckRequestDTO emailCheckRequestDTO) {
		String email = emailCheckRequestDTO.getEmail();
		String code = emailCheckRequestDTO.getCode();
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

	private String makeRandomCode() {
		return String.format("%06d", new Random().nextInt(1000000));
	}

	private Date makeExpire() {
		return new Date(AUTH_CHALLENGE_EXPIRE_TIME + System.currentTimeMillis());
	}

	@Override
	public void requestEmail(EmailRequestRequestDTO emailRequestRequestDTO) {
		Date expires = makeExpire();
		String code = makeRandomCode();
		String email = emailRequestRequestDTO.getEmail();
		mailSending.sendMail(email, "Email Verification Code", "Your verification code is " + code);
		authTaskMemory.recordAuthChallenge(email, code, expires);
	}

	@Override
	public void requestPhone(String phone) {
		Date expires = makeExpire();
		String code = makeRandomCode();
		// todo: Send SMS or Kakaotalk Alert talk
		authTaskMemory.recordAuthChallenge(phone, code, expires);
	}
}
