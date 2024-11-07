package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.out.dto.LoginDTO;
import com.promptoven.authservice.application.port.out.dto.RefreshDTO;

public interface AuthenticationUseCase {

	boolean checkPW(String password, String memberUUID);

	void changePW(String newPassword, String memberUUID);

	LoginDTO login(String email, String password);

	void logout(String AccessToken, String RefreshToken);

	void resetPW(String email, String password);

	void withdraw(String AccessToken);

	RefreshDTO refresh(String RefreshToken);
}
