package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;
import org.springframework.lang.Nullable;

public interface SocialLoginUseCase {

    SocialLoginDTO oauthLogin(String provider, String providerID, @Nullable String email);

    void OauthRegister(String provider, String providerID, String memberUUID);

    void OauthUnregister(String provider, String providerID, String memberUUID);

}
