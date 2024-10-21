package com.promptoven.authservice.application.port.in.usecase;

public interface OauthRegisterUseCase {

	void OauthRegister(String provider, String providerID, String memberUUID);
}
