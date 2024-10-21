package com.promptoven.authservice.application.port.in.usecase;

public interface OauthUnregisterUseCase {

	void OauthUnregister(String provider, String providerID, String memberUUID);
}
