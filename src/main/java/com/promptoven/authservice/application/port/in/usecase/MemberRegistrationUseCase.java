package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.in.dto.RegisterRequestDTO;
import com.promptoven.authservice.application.port.in.dto.RegisterSocialRequestDTO;
import com.promptoven.authservice.application.port.out.dto.LoginResponseDTO;

public interface MemberRegistrationUseCase {

	LoginResponseDTO register(RegisterRequestDTO registerRequestDTO);

	LoginResponseDTO registerFromSocialLogin(RegisterSocialRequestDTO registerSocialRequestDTO);

	void AdminRegister(RegisterRequestDTO registerRequestDTO);
}
