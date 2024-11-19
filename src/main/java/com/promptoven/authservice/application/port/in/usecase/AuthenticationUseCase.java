package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.in.dto.ChangePWRequestDTO;
import com.promptoven.authservice.application.port.in.dto.CheckPWRequestDTO;
import com.promptoven.authservice.application.port.in.dto.ResetPWRequestDTO;
import com.promptoven.authservice.application.port.out.dto.LoginResponseDTO;
import com.promptoven.authservice.application.port.out.dto.RefreshDTO;
import com.promptoven.authservice.application.service.LoginRequestDTO;

public interface AuthenticationUseCase {

	boolean checkPW(CheckPWRequestDTO checkPWRequestDTO);

	void changePW(ChangePWRequestDTO changePWRequestDTO);

	LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

	void logout(String AccessToken, String RefreshToken);

	void resetPW(ResetPWRequestDTO resetPWRequestDTO);

	void withdraw(String AccessToken);

	RefreshDTO refresh(String RefreshToken);
}
