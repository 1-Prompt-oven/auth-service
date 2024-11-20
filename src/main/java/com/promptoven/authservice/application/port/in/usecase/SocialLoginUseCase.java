package com.promptoven.authservice.application.port.in.usecase;

import java.util.List;

import com.promptoven.authservice.application.port.in.dto.SocialLoginAssociateRequestDTO;
import com.promptoven.authservice.application.port.in.dto.SocialLoginDisassociateRequestDTO;
import com.promptoven.authservice.application.port.in.dto.SocialLoginRequestDTO;
import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;
import com.promptoven.authservice.application.service.dto.OauthInfoDTO;

public interface SocialLoginUseCase {

	SocialLoginDTO SocialLogin(SocialLoginRequestDTO SocialLoginRequestDTO);

	void SocialLoginAssociate(SocialLoginAssociateRequestDTO SocialLoginAssociateRequestDTO);

	void SocialLoginDisassociate(SocialLoginDisassociateRequestDTO SocialLoginDisassociateRequestDTO);

	List<OauthInfoDTO> getSocialLoginAssociations(String memberUUID);
}
