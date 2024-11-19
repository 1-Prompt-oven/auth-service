package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.in.dto.RegisterRequestDTO;
import com.promptoven.authservice.application.port.in.dto.RegisterSocialRequestDTO;
import com.promptoven.authservice.application.port.in.dto.VerifyEmailRequestDTO;
import com.promptoven.authservice.application.port.in.dto.VerifyNicknameRequestDTO;
import com.promptoven.authservice.application.port.out.dto.LoginResponseDTO;

public interface MemberRegistrationUseCase {

	LoginResponseDTO register(RegisterRequestDTO registerRequestDTO);

	boolean verifyEmail(VerifyEmailRequestDTO verifyEmailRequestDTO);

	boolean verifyNickname(VerifyNicknameRequestDTO verifyNicknameRequestDTO);

	LoginResponseDTO registerFromSocialLogin(RegisterSocialRequestDTO registerSocialRequestDTO);

	void AdminRegister(RegisterRequestDTO registerRequestDTO);
}
