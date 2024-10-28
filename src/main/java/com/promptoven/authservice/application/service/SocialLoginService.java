package com.promptoven.authservice.application.service;

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
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
    public SocialLoginDTO oauthLogin(String provider, String providerID, @Nullable String email) {

        // Check if user already exists with this OAuth provider
        String existingMemberUUID = oauthInfoPersistence.getMemberUUID(provider, providerID);
        boolean isExistingMember = null != existingMemberUUID;

        // Handle email linking if provided
        if (null != email) {
            handleEmailLinking(email, provider, providerID);
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
        String accessToken = jwtProvider.issueJwt(memberUUID);
        String refreshToken = jwtProvider.issueRefresh(accessToken);

        return SocialLoginDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .role(role)
                .uuid(member.getUuid())
                .nickname(member.getNickname())
                .build();
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
}
