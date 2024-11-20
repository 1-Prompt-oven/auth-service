package com.promptoven.authservice.application.port.out.call;

import java.util.List;

import com.promptoven.authservice.application.service.dto.OauthInfoDTO;

public interface OauthInfoPersistence {

	void recordOauthInfo(OauthInfoDTO oauthInfoDTO);

	String getMemberUUID(String Provider, String ProviderID);

	List<OauthInfoDTO> getOauthInfo(String memberUUID);

	boolean existByOauthInfoDTO(OauthInfoDTO oauthInfoDTO);

	void deleteOauthInfo(OauthInfoDTO oauthInfoDTO);
}
