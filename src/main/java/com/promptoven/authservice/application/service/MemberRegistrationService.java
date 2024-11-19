package com.promptoven.authservice.application.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.dto.RegisterRequestDTO;
import com.promptoven.authservice.application.port.in.dto.RegisterSocialRequestDTO;
import com.promptoven.authservice.application.port.in.dto.VerifyEmailRequestDTO;
import com.promptoven.authservice.application.port.in.dto.VerifyNicknameRequestDTO;
import com.promptoven.authservice.application.port.in.usecase.MemberRegistrationUseCase;
import com.promptoven.authservice.application.port.out.call.EventPublisher;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.OauthInfoPersistence;
import com.promptoven.authservice.application.port.out.dto.LoginResponseDTO;
import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.OauthInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberRegistrationService implements MemberRegistrationUseCase {

	private final MemberPersistence memberPersistence;
	private final PasswordEncoder passwordEncoder;
	private final OauthInfoPersistence oauthInfoPersistence;
	private final AuthenticationService authenticationService;
	private final EventPublisher eventPublisher;

	@Override
	public LoginResponseDTO register(RegisterRequestDTO registerRequestDTO) {
		String email = registerRequestDTO.getEmail();
		String password = registerRequestDTO.getPassword();
		String nickname = registerRequestDTO.getNickname();
		String uuid = makeMember(email, password, nickname, 1);
		eventPublisher.publish("member-registered", uuid);
		return authenticationService.login(new LoginRequestDTO(email, password));
	}

	@Override
	public boolean verifyEmail(VerifyEmailRequestDTO verifyEmailRequestDTO) {
		return !memberPersistence.existsByEmail(verifyEmailRequestDTO.getEmail());
	}

	//todo: 닉네임 중복 체크 통과하면 5분 정도 점유를 할 수 있도록 구현 (Redis Cache 사용)
	@Override
	public boolean verifyNickname(VerifyNicknameRequestDTO verifyNicknameRequestDTO) {
		return !memberPersistence.existsByNickname(verifyNicknameRequestDTO.getNickname());
	}

	@Override
	public LoginResponseDTO registerFromSocialLogin(RegisterSocialRequestDTO registerSocialRequestDTO) {

		String email = registerSocialRequestDTO.getEmail();
		String password = registerSocialRequestDTO.getPassword();
		String nickname = registerSocialRequestDTO.getNickname();
		String provider = registerSocialRequestDTO.getProvider();
		String providerID = registerSocialRequestDTO.getProviderId();

		String uuid = makeMember(email, password, nickname, 1);
		OauthInfo oauthInfo = OauthInfo.createOauthInfo(provider, providerID, uuid);
		oauthInfoPersistence.recordOauthInfo(oauthInfo);
		eventPublisher.publish("member-registered", uuid);
		return authenticationService.login(new LoginRequestDTO(email, password));
	}

	@Override
	public void AdminRegister(RegisterRequestDTO registerRequestDTO) {
		String email = registerRequestDTO.getEmail();
		String password = registerRequestDTO.getPassword();
		String nickname = registerRequestDTO.getNickname();
		makeMember(email, password, nickname, 3);
	}

	private String makeMember(String email, String password, String nickname, int role) {
		String uuid = UUID.randomUUID().toString();
		String encodedPassword = passwordEncoder.encode(password);
		while (memberPersistence.findByUuid(uuid) != null) {
			uuid = UUID.randomUUID().toString();
		}
		Member member = Member.createMember
			(uuid, email, encodedPassword, nickname, role);
		memberPersistence.create(member);
		return uuid;
	}
}
