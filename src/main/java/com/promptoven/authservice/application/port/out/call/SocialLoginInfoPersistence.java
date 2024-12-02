package com.promptoven.authservice.application.port.out.call;

import java.util.List;

import com.promptoven.authservice.application.service.dto.SocialLoginInfoDTO;

public interface SocialLoginInfoPersistence {

	void recordSocialLoginInfo(SocialLoginInfoDTO socialLoginInfoDTO);

	String getMemberUUID(String Provider, String ProviderID);

	List<SocialLoginInfoDTO> getSocialLoginInfo(String memberUUID);

	boolean existBySocialLoginDTO(SocialLoginInfoDTO socialLoginInfoDTO);

	void deleteSocialLoginInfo(SocialLoginInfoDTO socialLoginInfoDTO);
}
