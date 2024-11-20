package com.promptoven.authservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promptoven.authservice.application.port.in.dto.SocialLoginAssociateRequestDTO;
import com.promptoven.authservice.application.port.in.dto.SocialLoginDisassociateRequestDTO;
import com.promptoven.authservice.application.port.in.dto.SocialLoginRequestDTO;
import com.promptoven.authservice.application.port.in.usecase.SocialLoginUseCase;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.port.out.call.SocialLoginInfoPersistence;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;
import com.promptoven.authservice.application.service.dto.MemberDTO;
import com.promptoven.authservice.application.service.dto.SocialLoginEmailHandlingDTO;
import com.promptoven.authservice.application.service.dto.SocialLoginInfoDTO;
import com.promptoven.authservice.application.service.dto.mapper.MemberDomainDTOMapper;
import com.promptoven.authservice.application.service.dto.mapper.OauthInfoDomainDTOMapper;
import com.promptoven.authservice.application.service.utility.JwtProvider;
import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.SocialLoginInfo;
import com.promptoven.authservice.domain.dto.SocialLoginInfoModelDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialLoginService implements SocialLoginUseCase {

	private final SocialLoginInfoPersistence socialLoginInfoPersistence;
	private final MemberPersistence memberPersistence;
	private final RolePersistence rolePersistence;
	private final VerificationService verificationService;
	private final JwtProvider jwtProvider;
	private final MemberDomainDTOMapper memberDomainDTOMapper;
	private final OauthInfoDomainDTOMapper oauthInfoDomainDTOMapper;

	@Override
	public SocialLoginDTO SocialLogin(SocialLoginRequestDTO socialLoginRequestDTO) {
		String provider = socialLoginRequestDTO.getProvider();
		String providerID = socialLoginRequestDTO.getProviderId();
		// Check if user already exists with this OAuth provider
		String existingMemberUUID = socialLoginInfoPersistence.getMemberUUID(provider, providerID);
		// Handle email linking if provided
		if (null != socialLoginRequestDTO.getEmail()) {
			SocialLoginEmailHandlingDTO socialLoginEmailHandlingDTO = SocialLoginEmailHandlingDTO.builder()
				.email(socialLoginRequestDTO.getEmail())
				.provider(provider)
				.providerId(providerID)
				.build();
			existingMemberUUID = handleEmailLinking(socialLoginEmailHandlingDTO);
		}
		boolean isExistingMember = null != existingMemberUUID;
		// Return login response if member exists
		if (isExistingMember) {
			return createLoginResponse(existingMemberUUID);
		}
		// if failed to social login, return DTO has isFailed=true
		return new SocialLoginDTO();
	}

	private String handleEmailLinking(SocialLoginEmailHandlingDTO socialLoginEmailHandlingDTO) {
		String email = socialLoginEmailHandlingDTO.getEmail();
		String provider = socialLoginEmailHandlingDTO.getProvider();
		String providerID = socialLoginEmailHandlingDTO.getProviderId();
		MemberDTO memberDTO = memberPersistence.findByEmail(email);
		if (null != memberDTO) {
			Member member = memberDomainDTOMapper.toDomain(memberDTO);
			SocialLoginInfoModelDTO socialLoginInfoModelDTO = SocialLoginInfoModelDTO.builder()
				.memberUUID(member.getUuid())
				.provider(provider)
				.providerID(providerID)
				.build();
			SocialLoginInfo socialLoginInfo = SocialLoginInfo.createSocialLoginInfo(socialLoginInfoModelDTO);
			socialLoginInfoPersistence.recordOauthInfo(oauthInfoDomainDTOMapper.toDTO(socialLoginInfo));
			return member.getUuid();
		} else {
			verificationService.saveSuccessAuthChallenge(email);
			return null;
		}
	}

	private SocialLoginDTO createLoginResponse(String memberUUID) {
		Member member = memberDomainDTOMapper.toDomain(memberPersistence.findByUuid(memberUUID));
		String role = rolePersistence.findRoleById(member.getRole()).getName();
		String accessToken = jwtProvider.issueJwt(memberUUID, role);
		String refreshToken = jwtProvider.issueRefresh(memberUUID);

		return SocialLoginDTO.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.role(role)
			.uuid(member.getUuid())
			.nickname(member.getNickname())
			.build();
	}

	@Override
	public void SocialLoginAssociate(SocialLoginAssociateRequestDTO socialLoginAssociateRequestDTO) {
		SocialLoginInfoModelDTO socialLoginInfoModelDTO = SocialLoginInfoModelDTO.builder()
			.memberUUID(socialLoginAssociateRequestDTO.getMemberUUID())
			.provider(socialLoginAssociateRequestDTO.getProvider())
			.providerID(socialLoginAssociateRequestDTO.getProviderId())
			.build();
		SocialLoginInfo socialLoginInfo = SocialLoginInfo.createSocialLoginInfo(socialLoginInfoModelDTO);
		socialLoginInfoPersistence.recordOauthInfo(oauthInfoDomainDTOMapper.toDTO(socialLoginInfo));
	}

	@Override
	@Transactional
	public void SocialLoginDisassociate(SocialLoginDisassociateRequestDTO socialLoginDisassociateRequestDTO) {

		SocialLoginInfoDTO requestedSocialLoginInfoDTO = SocialLoginInfoDTO.builder()
			.memberUUID(socialLoginDisassociateRequestDTO.getMemberUUID())
			.provider(socialLoginDisassociateRequestDTO.getProvider())
			.providerID(socialLoginDisassociateRequestDTO.getProviderId())
			.build();

		SocialLoginInfo requestedSocialLoginInfo = oauthInfoDomainDTOMapper.toDomain(requestedSocialLoginInfoDTO);

		try {
			socialLoginInfoPersistence.deleteOauthInfo(oauthInfoDomainDTOMapper.toDTO(requestedSocialLoginInfo));
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public List<SocialLoginInfoDTO> getSocialLoginAssociations(String accessToken) {
		String memberUUID = jwtProvider.validateAndDecryptToken(accessToken).getUserId();
		return socialLoginInfoPersistence.getOauthInfo(memberUUID);
	}
}
