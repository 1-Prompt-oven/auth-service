package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.out.dto.LoginDTO;

public interface MemberRegistrationUseCase {

    LoginDTO register(String email, String password, String nickname);

    boolean verifyEmail(String email);

    boolean verifyNickname(String nickname);

    LoginDTO registerFromSocialLogin(String email, String nickname, String password, String provider,
                                     String providerID);

    void AdminRegister(String email, String password, String nickname);
}
