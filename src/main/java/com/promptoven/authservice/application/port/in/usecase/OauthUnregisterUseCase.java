package com.promptoven.authservice.application.port.in.usecase;

public interface OauthUnregisterUseCase {

	void unregister(String provider, String providerID);
}
