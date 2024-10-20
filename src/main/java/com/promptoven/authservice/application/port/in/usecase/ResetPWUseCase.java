package com.promptoven.authservice.application.port.in.usecase;

public interface ResetPWUseCase {

	void resetPW(String email, String password);
}
