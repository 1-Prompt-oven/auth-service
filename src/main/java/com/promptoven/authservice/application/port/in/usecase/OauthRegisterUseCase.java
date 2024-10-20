package com.promptoven.authservice.application.port.in.usecase;

public interface OauthRegisterUseCase {

	void register(String provider, String providerID);
}
