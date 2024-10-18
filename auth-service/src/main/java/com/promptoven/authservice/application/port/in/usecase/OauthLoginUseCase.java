package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;

public interface OauthLoginUseCase {
	SocialLoginDTO oauthLogin(String provider, String providerID);
}
