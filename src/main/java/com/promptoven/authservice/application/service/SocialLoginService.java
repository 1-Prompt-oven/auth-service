package com.promptoven.authservice.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promptoven.authservice.application.port.in.dto.SocialLoginAssociateRequestDTO;
import com.promptoven.authservice.application.port.in.dto.SocialLoginDisassociateRequestDTO;
import com.promptoven.authservice.application.port.in.dto.SocialLoginRequestDTO;
import com.promptoven.authservice.application.port.in.usecase.SocialLoginUseCase;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.OauthInfoPersistence;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;
import com.promptoven.authservice.application.service.dto.MemberDTO;
import com.promptoven.authservice.application.service.dto.OauthInfoDTO;
import com.promptoven.authservice.application.service.dto.mapper.MemberDomainDTOMapper;
import com.promptoven.authservice.application.service.dto.mapper.OauthInfoDomainDTOMapper;
import com.promptoven.authservice.application.service.utility.JwtProvider;
import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.OauthInfo;
import com.promptoven.authservice.domain.dto.OauthInfoModelDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocialLoginService implements SocialLoginUseCase {

	private final OauthInfoPersistence oauthInfoPersistence;
	private final MemberPersistence memberPersistence;
	private final RolePersistence rolePersistence;
	private final MediaAuthService mediaAuthService;
	private final JwtProvider jwtProvider;
	private final MemberDomainDTOMapper memberDomainDTOMapper;
	private final OauthInfoDomainDTOMapper oauthInfoDomainDTOMapper;

	@Override
	public SocialLoginDTO SocialLogin(SocialLoginRequestDTO socialLoginRequestDTO) {
		String provider = socialLoginRequestDTO.getProvider();
		String providerID = socialLoginRequestDTO.getProviderId();
		// Check if user already exists with this OAuth provider
		String existingMemberUUID = oauthInfoPersistence.getMemberUUID(provider, providerID);
		boolean isExistingMember = null != existingMemberUUID;

		// Handle email linking if provided
		if (null != socialLoginRequestDTO.getEmail()) {
			handleEmailLinking(socialLoginRequestDTO.getEmail(), provider, providerID);
		}
		// Return login response if member exists
		if (isExistingMember) {
			return createLoginResponse(existingMemberUUID);
		}
		// if failed to social login, return DTO has isFailed=true
		return new SocialLoginDTO();
	}

	private void handleEmailLinking(String email, String provider, String providerID) {
		MemberDTO memberDTO = memberPersistence.findByEmail(email);
		if (null != memberDTO) {
			Member member = memberDomainDTOMapper.toDomain(memberDTO);
			OauthInfoModelDTO oauthInfoModelDTO = OauthInfoModelDTO.builder()
				.memberUUID(member.getUuid())
				.provider(provider)
				.providerID(providerID)
				.build();
			OauthInfo oauthInfo = OauthInfo.createOauthInfo(oauthInfoModelDTO);
			oauthInfoPersistence.recordOauthInfo(oauthInfoDomainDTOMapper.toDTO(oauthInfo));
		} else {
			mediaAuthService.saveSuccessAuthChallenge(email);
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
		OauthInfoModelDTO oauthInfoModelDTO = OauthInfoModelDTO.builder()
			.memberUUID(socialLoginAssociateRequestDTO.getMemberUUID())
			.provider(socialLoginAssociateRequestDTO.getProvider())
			.providerID(socialLoginAssociateRequestDTO.getProviderId())
			.build();
		OauthInfo oauthInfo = OauthInfo.createOauthInfo(oauthInfoModelDTO);
		oauthInfoPersistence.recordOauthInfo(oauthInfoDomainDTOMapper.toDTO(oauthInfo));
	}

	@Override
	@Transactional
	public void SocialLoginDisassociate(SocialLoginDisassociateRequestDTO socialLoginDisassociateRequestDTO) {

		OauthInfoDTO requestedOauthInfoDTO = OauthInfoDTO.builder()
			.memberUUID(socialLoginDisassociateRequestDTO.getMemberUUID())
			.provider(socialLoginDisassociateRequestDTO.getProvider())
			.providerID(socialLoginDisassociateRequestDTO.getProviderId())
			.build();

		OauthInfo requestedOauthInfo = oauthInfoDomainDTOMapper.toDomain(requestedOauthInfoDTO);

		try {
			oauthInfoPersistence.deleteOauthInfo(oauthInfoDomainDTOMapper.toDTO(requestedOauthInfo));
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public List<OauthInfoDTO> getSocialLoginAssociations(String accessToken) {
		String memberUUID = jwtProvider.validateAndDecryptToken(accessToken).getUserId();
		return oauthInfoPersistence.getOauthInfo(memberUUID);
	}
}
