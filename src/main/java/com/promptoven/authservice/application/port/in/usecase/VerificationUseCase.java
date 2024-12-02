package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.in.dto.EmailCheckRequestDTO;
import com.promptoven.authservice.application.port.in.dto.EmailRequestRequestDTO;
import com.promptoven.authservice.application.port.in.dto.VerifyEmailRequestDTO;
import com.promptoven.authservice.application.port.in.dto.VerifyNicknameRequestDTO;

public interface VerificationUseCase {

	boolean checkMedia(EmailCheckRequestDTO emailCheckRequestDTO);

	void requestEmail(EmailRequestRequestDTO emailRequestRequestDTO);

	void requestPhone(String phone);

	boolean verifyEmail(VerifyEmailRequestDTO verifyEmailRequestDTO);

	boolean verifyNickname(VerifyNicknameRequestDTO verifyNicknameRequestDTO);

}
