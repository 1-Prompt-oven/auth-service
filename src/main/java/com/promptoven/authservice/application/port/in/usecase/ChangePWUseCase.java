package com.promptoven.authservice.application.port.in.usecase;

//todo: 모든 usecase는 Service method의 interface이어야 해요

public interface ChangePWUseCase {

	void changePW(String oldPassword, String newPassword, String memberUUID);
}
