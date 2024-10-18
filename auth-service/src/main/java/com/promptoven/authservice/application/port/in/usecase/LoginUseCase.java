package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.out.dto.LoginDTO;

public interface LoginUseCase {
	LoginDTO login(String email, String password);
}
