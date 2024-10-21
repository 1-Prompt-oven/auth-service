package com.promptoven.authservice.application.port.out.call;

import java.util.List;

import com.promptoven.authservice.domain.OauthInfo;

public interface OauthInfoPersistence {

	void recordOauthInfo(OauthInfo oauthInfo);

	String getMemberUUID(String Provider, String ProviderID);

	List<OauthInfo> getOauthInfo(String memberUUID);

	void deleteOauthInfo(String memberUUID, String provider, String providerID);
}
