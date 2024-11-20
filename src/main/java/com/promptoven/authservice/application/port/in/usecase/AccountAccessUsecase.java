package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.in.dto.CheckPWRequestDTO;
import com.promptoven.authservice.application.port.out.dto.LoginResponseDTO;
import com.promptoven.authservice.application.port.out.dto.RefreshDTO;
import com.promptoven.authservice.application.service.dto.LoginRequestDTO;

public interface AccountAccessUsecase {

	boolean checkPW(CheckPWRequestDTO checkPWRequestDTO);

	LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

	void logout(String AccessToken, String RefreshToken);

	void withdraw(String AccessToken);

	RefreshDTO refresh(String RefreshToken);
}
