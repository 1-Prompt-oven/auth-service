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
import com.promptoven.authservice.application.service.dto.LoginRequestDTO;
import com.promptoven.authservice.application.service.dto.OauthInfoDTO;
import com.promptoven.authservice.application.service.dto.mapper.MemberDomainDTOMapper;
import com.promptoven.authservice.application.service.dto.mapper.OauthInfoDomainDTOMapper;
import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.OauthInfo;
import com.promptoven.authservice.domain.dto.MemberModelDTO;
import com.promptoven.authservice.domain.dto.OauthInfoModelDTO;

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
	private final OauthInfoDomainDTOMapper oauthInfoDomainDTOMapper;
	private final MemberDomainDTOMapper memberDomainDTOMapper;

	@Override
	public LoginResponseDTO register(RegisterRequestDTO registerRequestDTO) {
		String uuid = makeMember(registerRequestDTO, 1);
		eventPublisher.publish("member-registered", uuid);
		return authenticationService.login(LoginRequestDTO.builder()
			.email(registerRequestDTO.getEmail())
			.password(registerRequestDTO.getPassword())
			.build());
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

		String provider = registerSocialRequestDTO.getProvider();
		String providerID = registerSocialRequestDTO.getProviderId();

		String uuid = makeMember(registerSocialRequestDTO.toRegisterRequestDTO(), 1);
		OauthInfoModelDTO oauthInfoModelDTO = OauthInfoModelDTO.builder()
			.memberUUID(uuid)
			.provider(provider)
			.providerID(providerID)
			.build();
		OauthInfoDTO oauthInfoDTO = oauthInfoDomainDTOMapper.toDTO(
			OauthInfo.createOauthInfo(oauthInfoModelDTO));
		oauthInfoPersistence.recordOauthInfo(oauthInfoDTO);
		eventPublisher.publish("member-registered", uuid);
		return authenticationService.login(new LoginRequestDTO(email, password));
	}

	@Override
	public void AdminRegister(RegisterRequestDTO registerRequestDTO) {
		makeMember(registerRequestDTO, 3);
	}

	private String makeMember(RegisterRequestDTO registerRequestDTO, int role) {
		
		String uuid = UUID.randomUUID().toString();
		String encodedPassword = passwordEncoder.encode(registerRequestDTO.getPassword());
		while (memberPersistence.findByUuid(uuid) != null) {
			uuid = UUID.randomUUID().toString();
		}
		MemberModelDTO memberModelDTO = MemberModelDTO.builder()
			.uuid(uuid)
			.email(registerRequestDTO.getEmail())
			.password(encodedPassword)
			.nickname(registerRequestDTO.getNickname())
			.role(role)
			.build();
		Member member = Member.createMember(memberModelDTO);
		memberPersistence.create(memberDomainDTOMapper.toDTO(member));
		return uuid;
	}
}
