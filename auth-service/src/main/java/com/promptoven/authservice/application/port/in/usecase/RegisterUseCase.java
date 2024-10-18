package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.out.dto.LoginDTO;

public interface RegisterUseCase {

	LoginDTO register(String email, String password, String nickname);
}
