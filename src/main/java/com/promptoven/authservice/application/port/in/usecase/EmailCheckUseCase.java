package com.promptoven.authservice.application.port.in.usecase;

public interface EmailCheckUseCase {
	boolean checkMedia(String email, String code);
}
