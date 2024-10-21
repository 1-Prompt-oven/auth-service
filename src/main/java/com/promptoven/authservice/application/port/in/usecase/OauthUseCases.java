package com.promptoven.authservice.application.port.in.usecase;

import org.springframework.lang.Nullable;

import com.promptoven.authservice.application.port.out.dto.LoginDTO;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;

public interface OauthUseCases {

	SocialLoginDTO oauthLogin(String provider, String providerID, @Nullable String email);

	void OauthRegister(String provider, String providerID, String memberUUID);

	void OauthUnregister(String provider, String providerID, String memberUUID);

	LoginDTO registerFromSocialLogin(String email, String nickname, String password, String provider,
		String providerID);
}
