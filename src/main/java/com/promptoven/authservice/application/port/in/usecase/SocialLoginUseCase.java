package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.in.dto.OauthLoginRequestDTO;
import com.promptoven.authservice.application.port.in.dto.OauthRegisterRequestDTO;
import com.promptoven.authservice.application.port.in.dto.OauthUnregisterRequestDTO;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;

public interface SocialLoginUseCase {

	SocialLoginDTO oauthLogin(OauthLoginRequestDTO OauthLoginRequestDTO);

	void OauthRegister(OauthRegisterRequestDTO OauthRegisterRequestDTO);

	void OauthUnregister(OauthUnregisterRequestDTO OauthUnregisterRequestDTO);

}
