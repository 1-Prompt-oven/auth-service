package com.promptoven.authservice.application.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.dto.RegisterRequestDTO;
import com.promptoven.authservice.application.port.in.dto.RegisterSocialRequestDTO;
import com.promptoven.authservice.application.port.in.usecase.MemberRegistrationUseCase;
import com.promptoven.authservice.application.port.out.call.EventPublisher;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.SocialLoginInfoPersistence;
import com.promptoven.authservice.application.port.out.dto.LoginResponseDTO;
import com.promptoven.authservice.application.service.dto.LoginRequestDTO;
import com.promptoven.authservice.application.service.dto.SocialLoginInfoDTO;
import com.promptoven.authservice.application.service.dto.mapper.MemberDomainDTOMapper;
import com.promptoven.authservice.application.service.dto.mapper.OauthInfoDomainDTOMapper;
import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.SocialLoginInfo;
import com.promptoven.authservice.domain.dto.MemberModelDTO;
import com.promptoven.authservice.domain.dto.SocialLoginInfoModelDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberRegistrationService implements MemberRegistrationUseCase {

	private final MemberPersistence memberPersistence;
	private final PasswordEncoder passwordEncoder;
	private final SocialLoginInfoPersistence socialLoginInfoPersistence;
	private final AccountAccessService accountAccessService;
	private final EventPublisher eventPublisher;
	private final OauthInfoDomainDTOMapper oauthInfoDomainDTOMapper;
	private final MemberDomainDTOMapper memberDomainDTOMapper;
	@Value("${member-registered-event}")
	private String MemberRegisteredTopic;

	@Override
	public LoginResponseDTO register(RegisterRequestDTO registerRequestDTO) {
		String uuid = makeMember(registerRequestDTO, 1);
		eventPublisher.publish(MemberRegisteredTopic, uuid);
		return accountAccessService.login(LoginRequestDTO.builder()
			.email(registerRequestDTO.getEmail())
			.password(registerRequestDTO.getPassword())
			.build());
	}

	@Override
	public LoginResponseDTO registerFromSocialLogin(RegisterSocialRequestDTO registerSocialRequestDTO) {

		String email = registerSocialRequestDTO.getEmail();
		String password = registerSocialRequestDTO.getPassword();

		String provider = registerSocialRequestDTO.getProvider();
		String providerID = registerSocialRequestDTO.getProviderId();

		String uuid = makeMember(registerSocialRequestDTO.toRegisterRequestDTO(), 1);
		SocialLoginInfoModelDTO socialLoginInfoModelDTO = SocialLoginInfoModelDTO.builder()
			.memberUUID(uuid)
			.provider(provider)
			.providerID(providerID)
			.build();
		SocialLoginInfoDTO socialLoginInfoDTO = oauthInfoDomainDTOMapper.toDTO(
			SocialLoginInfo.createSocialLoginInfo(socialLoginInfoModelDTO));
		socialLoginInfoPersistence.recordSocialLoginInfo(socialLoginInfoDTO);
		eventPublisher.publish("member-registered", uuid);
		return accountAccessService.login(LoginRequestDTO.builder()
			.email(email)
			.password(password)
			.build());
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
