package com.promptoven.authservice.application.port.in.usecase;

//todo: auth 계열 하나, oauth 계열 하나 식으로 Use case 묶어보기 :: Method 단위 분리는 지나친 추상화임

public interface ChangePWUseCase {

	boolean changePW(String oldPassword, String newPassword, String memberUUID);
}
