package com.promptoven.authservice.application.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.usecase.MemberRegistrationUseCase;
import com.promptoven.authservice.application.port.out.call.EventPublisher;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.OauthInfoPersistence;
import com.promptoven.authservice.application.port.out.dto.LoginDTO;
import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.OauthInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberRegistrationService implements MemberRegistrationUseCase {

	private final MemberPersistence memberPersistence;
	private final PasswordEncoder passwordEncoder;
	private final OauthInfoPersistence oauthInfoPersistence;
	private final AuthenticationService authenticationService;
	private final EventPublisher eventPublisher;

	public MemberRegistrationService(MemberPersistence memberPersistence, PasswordEncoder passwordEncoder,
		OauthInfoPersistence oauthInfoPersistence, AuthenticationService authenticationService,
		@Qualifier("eventPublisherByKafka") EventPublisher eventPublisher) {
		this.memberPersistence = memberPersistence;
		this.passwordEncoder = passwordEncoder;
		this.oauthInfoPersistence = oauthInfoPersistence;
		this.authenticationService = authenticationService;
		this.eventPublisher = eventPublisher;
	}

	@Override
	public LoginDTO register(String email, String password, String nickname) {
		String uuid = makeMember(email, password, nickname, 1);
		eventPublisher.publish("member-registered", uuid);
		return authenticationService.login(email, password);
	}

	@Override
	public boolean verifyEmail(String email) {
		return !memberPersistence.existsByEmail(email);
	}

	@Override
	public boolean verifyNickname(String nickname) {
		return !memberPersistence.existsByNickname(nickname);
	}

	@Override
	public LoginDTO registerFromSocialLogin(String email, String nickname, String password, String provider,
		String providerID) {
		String uuid = makeMember(email, password, nickname, 1);
		OauthInfo oauthInfo = OauthInfo.createOauthInfo(provider, providerID, uuid);
		oauthInfoPersistence.recordOauthInfo(oauthInfo);
		eventPublisher.publish("member-registered", uuid);
		return authenticationService.login(email, password);
	}

	@Override
	public void AdminRegister(String email, String password, String nickname) {
		makeMember(email, password, nickname, 3);
	}

	private String makeMember(String email, String password, String nickname, int role) {
		String uuid = UUID.randomUUID().toString();
		String encodedPassword = passwordEncoder.encode(password);
		Member member = Member.createMember
			(uuid, email, encodedPassword, nickname, role);
		while (memberPersistence.findByUuid(uuid) != null) {
			uuid = UUID.randomUUID().toString();
		}
		memberPersistence.create(member);
		return uuid;
	}
}
