package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.out.dto.LoginDTO;

public interface AuthUseCases {

	boolean checkPW(String password, String memberUUID);

	void changePW(String newPassword, String memberUUID);

	boolean checkMedia(String email, String code);

	void requestEmail(String email);

	LoginDTO login(String email, String password);

	void logout(String AccessToken, String RefreshToken);

	LoginDTO register(String email, String password, String nickname);

	void resetPW(String email, String password);

	boolean verifyEmail(String email);

	boolean verifyNickname(String nickname);

	void withdraw(String AccessToken);

	void AdminRegister(String email, String password, String nickname);
}
