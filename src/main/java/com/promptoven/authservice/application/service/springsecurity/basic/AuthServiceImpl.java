package com.promptoven.authservice.application.service.springsecurity.basic;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promptoven.authservice.application.port.in.usecase.ChangePWUseCase;
import com.promptoven.authservice.application.port.in.usecase.EmailCheckUseCase;
import com.promptoven.authservice.application.port.in.usecase.EmailRequestUseCase;
import com.promptoven.authservice.application.port.in.usecase.LoginUseCase;
import com.promptoven.authservice.application.port.in.usecase.LogoutUseCase;
import com.promptoven.authservice.application.port.in.usecase.OauthLoginUseCase;
import com.promptoven.authservice.application.port.in.usecase.OauthRegisterUseCase;
import com.promptoven.authservice.application.port.in.usecase.OauthUnregisterUseCase;
import com.promptoven.authservice.application.port.in.usecase.RegisterFromSocialLoginUseCase;
import com.promptoven.authservice.application.port.in.usecase.RegisterUseCase;
import com.promptoven.authservice.application.port.in.usecase.ResetPWUseCase;
import com.promptoven.authservice.application.port.in.usecase.VerifyEmailUseCase;
import com.promptoven.authservice.application.port.in.usecase.VerifyNicknameUseCase;
import com.promptoven.authservice.application.port.in.usecase.WithdrawUseCase;
import com.promptoven.authservice.application.port.out.call.AuthRepository;
import com.promptoven.authservice.application.port.out.call.MailService;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.OauthInfoPersistence;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.port.out.dto.LoginDTO;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;
import com.promptoven.authservice.application.service.utility.JwtProvider;
import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.OauthInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("authServiceBySpringSecurity")
@RequiredArgsConstructor
public class AuthServiceImpl
	implements ChangePWUseCase, EmailCheckUseCase, EmailRequestUseCase, LoginUseCase,
	OauthLoginUseCase, OauthRegisterUseCase, OauthUnregisterUseCase,
	RegisterFromSocialLoginUseCase, RegisterUseCase, VerifyNicknameUseCase,
	VerifyEmailUseCase, LogoutUseCase, WithdrawUseCase, ResetPWUseCase {

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
	public boolean changePW(String oldPassword, String newPassword, String memberUUID) {
		Member member = memberPersistence.findByUuid(memberUUID);
		if (passwordEncoder.matches(oldPassword, member.getPassword())) {
			memberPersistence.updatePassword(Member.updateMemberPassword(member, passwordEncoder.encode(newPassword)));
			return true;
		}
		return false;
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
			String role = rolePersistence.findRoleById(member.getRole()).getName();
			String accessToken = jwtProvider.issueJwt(member.getUuid());
			String refreshToken = jwtProvider.issueRefresh(accessToken);
			return LoginDTO.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.role(role)
				.uuid(member.getUuid())
				.nickname(member.getNickname())
				.build();
		}
		return null;
	}

	@Override
	public SocialLoginDTO oauthLogin(String provider, String providerID, @Nullable String email) {
		String memberUUID = oauthInfoPersistence.getMemberUUID(provider, providerID);
		if (memberUUID != null) {
			Member member = memberPersistence.findByUuid(memberUUID);
			String role = rolePersistence.findRoleById(member.getRole()).getName();
			String accessToken = jwtProvider.issueJwt(member.getUuid());
			String refreshToken = jwtProvider.issueRefresh(accessToken);
			return SocialLoginDTO.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.role(role)
				.uuid(member.getUuid())
				.nickname(member.getNickname())
				.build();
		} else if (email != null) {
			Member member = memberPersistence.findByEmail(email);
			if (member != null) {
				OauthInfo oauthInfo = OauthInfo.createOauthInfo(provider, providerID, member.getUuid());
				oauthInfoPersistence.recordOauthInfo(oauthInfo);
				String role = rolePersistence.findRoleById(member.getRole()).getName();
				String accessToken = jwtProvider.issueJwt(member.getUuid());
				String refreshToken = jwtProvider.issueRefresh(accessToken);
				return SocialLoginDTO.builder()
					.accessToken(accessToken)
					.refreshToken(refreshToken)
					.role(role)
					.uuid(member.getUuid())
					.nickname(member.getNickname())
					.build();
			} else {
				Date expires = new Date(AUTH_CHALLENGE_EXPIRE_TIME + System.currentTimeMillis());
				authRepository.recordAuthChallengeSuccess(email, expires);
			}
		}
		return new SocialLoginDTO();
	}

	@Override
	public void OauthRegister(String provider, String providerID, String memberUUID) {
		OauthInfo oauthInfo = OauthInfo.createOauthInfo(provider, providerID, memberUUID);
		oauthInfoPersistence.recordOauthInfo(oauthInfo);
	}

	@Override
	@Transactional
	public void OauthUnregister(String provider, String providerID, String memberUUID) {
		log.info("Attempting to unregister OAuth: provider={}, providerID={}, memberUUID={}", provider, providerID,
			memberUUID);
		try {
			oauthInfoPersistence.deleteOauthInfo(memberUUID, provider, providerID);
			log.info("OAuth unregistration successful");
		} catch (Exception e) {
			log.error("Error during OAuth unregistration", e);
			throw e;
		}
	}

	@Override
	public LoginDTO registerFromSocialLogin(String email, String nickname, String password, String provider,
		String providerID) {
		String uuid = UUID.randomUUID().toString();
		String encodedPassword = passwordEncoder.encode(password);
		Member member = Member.createMember
			(uuid, email, encodedPassword, nickname, LocalDateTime.now(), 1);
		while (memberPersistence.findByUuid(uuid) != null) {
			uuid = UUID.randomUUID().toString();
		}
		memberPersistence.create(member);
		OauthInfo oauthInfo = OauthInfo.createOauthInfo(provider, providerID, uuid);
		oauthInfoPersistence.recordOauthInfo(oauthInfo);
		return login(email, password);
	}

	@Override
	public LoginDTO register(String email, String password, String nickname) {
		String uuid = UUID.randomUUID().toString();
		String encodedPassword = passwordEncoder.encode(password);
		Member member = Member.createMember
			(uuid, email, encodedPassword, nickname, LocalDateTime.now(), 1);
		while (memberPersistence.findByUuid(uuid) != null) {
			uuid = UUID.randomUUID().toString();
		}
		memberPersistence.create(member);
		return login(email, password);
	}

	@Override
	public boolean verifyNickname(String nickname) {
		return !memberPersistence.existsByNickname(nickname);
	}

	@Override
	public boolean verifyEmail(String email) {
		return !memberPersistence.existsByEmail(email);
	}

	@Override
	public void logout(String accessToken, String refreshToken) {
		authRepository.blockToken(accessToken, jwtProvider.getTokenExpiration(accessToken));
		authRepository.blockToken(refreshToken, jwtProvider.getTokenExpiration(refreshToken));
	}

	@Override
	public void withdraw(String accessToken) {
		memberPersistence.remove(Member.deleteMember(memberPersistence.findByUuid(
			jwtProvider.getClaimOfToken(accessToken, "subject"))));
	}

	@Override
	public void resetPW(String email, String password) {
		Member member = memberPersistence.findByEmail(email);
		memberPersistence.updatePassword(Member.updateMemberPassword(member, passwordEncoder.encode(password)));
	}
}