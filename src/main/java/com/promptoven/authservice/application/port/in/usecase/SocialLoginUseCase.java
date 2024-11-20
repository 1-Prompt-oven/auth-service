package com.promptoven.authservice.application.port.in.usecase;

import java.util.List;

import com.promptoven.authservice.application.port.in.dto.OauthLoginRequestDTO;
import com.promptoven.authservice.application.port.in.dto.OauthRegisterRequestDTO;
import com.promptoven.authservice.application.port.in.dto.OauthUnregisterRequestDTO;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;
import com.promptoven.authservice.application.service.dto.OauthInfoDTO;

public interface SocialLoginUseCase {

	SocialLoginDTO oauthLogin(OauthLoginRequestDTO OauthLoginRequestDTO);

	void OauthRegister(OauthRegisterRequestDTO OauthRegisterRequestDTO);

	void OauthUnregister(OauthUnregisterRequestDTO OauthUnregisterRequestDTO);

	List<OauthInfoDTO> getOauthInfo(String memberUUID);
}
