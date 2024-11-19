package com.promptoven.authservice.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.promptoven.authservice.application.port.in.dto.OauthLoginRequestDTO;
import com.promptoven.authservice.application.port.in.dto.OauthRegisterRequestDTO;
import com.promptoven.authservice.application.port.in.dto.OauthUnregisterRequestDTO;
import com.promptoven.authservice.application.port.in.usecase.SocialLoginUseCase;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.OauthInfoPersistence;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;
import com.promptoven.authservice.application.service.utility.JwtProvider;
import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.OauthInfo;

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

	@Override
	public SocialLoginDTO oauthLogin(OauthLoginRequestDTO oauthLoginRequestDTO) {
		String provider = oauthLoginRequestDTO.getProvider();
		String providerID = oauthLoginRequestDTO.getProviderId();
		// Check if user already exists with this OAuth provider
		String existingMemberUUID = oauthInfoPersistence.getMemberUUID(provider, providerID);
		boolean isExistingMember = null != existingMemberUUID;

		// Handle email linking if provided
		if (null != oauthLoginRequestDTO.getEmail()) {
			handleEmailLinking(oauthLoginRequestDTO.getEmail(), provider, providerID);
		}
		// Return login response if member exists
		if (isExistingMember) {
			return createLoginResponse(existingMemberUUID);
		}
		// if failed to social login, return DTO has isFailed=true
		return new SocialLoginDTO();
	}

	private void handleEmailLinking(String email, String provider, String providerID) {
		Member member = memberPersistence.findByEmail(email);
		if (null != member) {
			OauthInfo oauthInfo = OauthInfo.createOauthInfo(provider, providerID, member.getUuid());
			oauthInfoPersistence.recordOauthInfo(oauthInfo);
		} else {
			mediaAuthService.saveSuccessAuthChallenge(email);
		}
	}

	private SocialLoginDTO createLoginResponse(String memberUUID) {
		Member member = memberPersistence.findByUuid(memberUUID);
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
	public void OauthRegister(OauthRegisterRequestDTO oauthRegisterRequestDTO) {
		OauthInfo oauthInfo = OauthInfo.createOauthInfo(
			oauthRegisterRequestDTO.getProvider(),
			oauthRegisterRequestDTO.getProviderId(),
			oauthRegisterRequestDTO.getMemberUUID());

		oauthInfoPersistence.recordOauthInfo(oauthInfo);
	}

	@Override
	@Transactional
	public void OauthUnregister(OauthUnregisterRequestDTO oauthUnregisterRequestDTO) {

		try {
			oauthInfoPersistence.deleteOauthInfo(
				oauthUnregisterRequestDTO.getProvider(),
				oauthUnregisterRequestDTO.getProviderId(),
				oauthUnregisterRequestDTO.getMemberUUID()
			);
		} catch (Exception e) {
			throw e;
		}
	}
}
