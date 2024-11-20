package com.promptoven.authservice.application.port.out.call;

import java.util.List;

import com.promptoven.authservice.application.service.dto.SocialLoginInfoDTO;

public interface SocialLoginInfoPersistence {

	void recordOauthInfo(SocialLoginInfoDTO socialLoginInfoDTO);

	String getMemberUUID(String Provider, String ProviderID);

	List<SocialLoginInfoDTO> getOauthInfo(String memberUUID);

	boolean existByOauthInfoDTO(SocialLoginInfoDTO socialLoginInfoDTO);

	void deleteOauthInfo(SocialLoginInfoDTO socialLoginInfoDTO);
}
