package com.promptoven.authservice.application.service;

import com.promptoven.authservice.application.port.in.usecase.MemberRegistrationUseCase;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.OauthInfoPersistence;
import com.promptoven.authservice.application.port.out.dto.LoginDTO;
import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.OauthInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberRegistrationService implements MemberRegistrationUseCase {

    private final MemberPersistence memberPersistence;
    private final PasswordEncoder passwordEncoder;
    private final OauthInfoPersistence oauthInfoPersistence;
    private final AuthenticationService authenticationService;

    @Override
    public LoginDTO register(String email, String password, String nickname) {
        makeMember(email, password, nickname, 1);
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
    public LoginDTO registerFromSocialLogin(String email, String nickname, String password, String provider, String providerID) {
        String uuid = makeMember(email, password, nickname, 1);
        OauthInfo oauthInfo = OauthInfo.createOauthInfo(provider, providerID, uuid);
        oauthInfoPersistence.recordOauthInfo(oauthInfo);
        return authenticationService.login(email, password);
    }

    @Override
    public void AdminRegister(String email, String password, String nickname) {
        makeMember(email, password, nickname, 3);
    }

    protected String makeMember(String email, String password, String nickname, int role) {
        String uuid = UUID.randomUUID().toString();
        String encodedPassword = passwordEncoder.encode(password);
        Member member = Member.createMember
                (uuid, email, encodedPassword, nickname, LocalDateTime.now(), role);
        while (memberPersistence.findByUuid(uuid) != null) {
            uuid = UUID.randomUUID().toString();
        }
        memberPersistence.create(member);
        return uuid;
    }
}
