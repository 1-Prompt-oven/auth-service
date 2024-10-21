package com.promptoven.authservice.application.port.in.usecase;

public interface LogoutUseCase {

	void logout(String AccessToken, String RefreshToken);
}
