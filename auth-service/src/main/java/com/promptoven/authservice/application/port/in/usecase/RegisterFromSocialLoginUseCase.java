package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.out.dto.LoginDTO;

public interface RegisterFromSocialLoginUseCase {

	LoginDTO registerFromSocialLogin(String email, String nickname, String password, String provider,
		String providerID);
}
