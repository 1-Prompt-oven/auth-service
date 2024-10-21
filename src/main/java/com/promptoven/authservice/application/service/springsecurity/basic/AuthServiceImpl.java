package com.promptoven.authservice.application.service.springsecurity.basic;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.promptoven.authservice.application.port.out.call.AuthRepository;
import com.promptoven.authservice.application.port.out.call.MailService;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.OauthInfoPersistence;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.port.out.dto.LoginDTO;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;
import com.promptoven.authservice.application.service.utility.JwtProvider;
import com.promptoven.authservice.domain.Member;

import lombok.RequiredArgsConstructor;

@Service("authServiceBySpringSecurity")
@RequiredArgsConstructor
public class AuthServiceImpl
	implements ChangePWUseCase, EmailCheckUseCase, EmailRequestUseCase, LoginUseCase,
	OauthLoginUseCase, OauthRegisterUseCase, OauthUnregisterUseCase,
	RegisterFromSocialLoginUseCase, RegisterUseCase, VerifyNicknameUseCase,
	VerifyEmailUseCase {

	private final MemberPersistence memberPersistence;
	private final OauthInfoPersistence oauthInfoPersistence;
	private final RolePersistence rolePersistence;
	private final AuthRepository authRepository;
	private final MailService mailService;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	@Value("${auth.challenge.expiration}")
	private long AUTH_CHALLENGE_EXPIRE_TIME;

	@Override
	public void changePW(String oldPassword, String newPassword, String memberUUID) {

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
		Member member = memberPersistence.findByEmail(email);
		if (member != null && passwordEncoder.matches(password, member.getPassword())) {
			return LoginDTO.builder()
				.accessToken("accessToken")
				.refreshToken("refreshToken")
				.role(rolePersistence.findRoleById(member.getRole()).getName())
				.uuid(member.getUuid())
				.nickname(member.getNickname())
				.build();
		}
		return null;
	}

	@Override
	public SocialLoginDTO oauthLogin(String provider, String providerID) {
		return null;
	}

	@Override
	public void OauthRegister(String provider, String providerID, String memberUUID) {

	}

	@Override
	public void OauthUnregister(String provider, String providerID, String memberUUID) {

	}

	@Override
	public LoginDTO registerFromSocialLogin(String email, String nickname, String password, String provider,
		String providerID) {
		return null;
	}

	@Override
	public LoginDTO register(String email, String password, String nickname) {
		String uuid = UUID.randomUUID().toString();
		Member member = Member.createMember
			(uuid, email, passwordEncoder.encode(password), nickname, LocalDateTime.now(), 1);
		while (memberPersistence.findByUuid(uuid) != null) {
			uuid = UUID.randomUUID().toString();
		}
		memberPersistence.create(member);
		return LoginDTO.builder()
			.accessToken("accessToken")
			.refreshToken("refreshToken")
			.role(rolePersistence.findRoleById(member.getRole()).getName())
			.uuid(member.getUuid())
			.nickname(member.getNickname())
			.build();
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
