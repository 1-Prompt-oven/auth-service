package com.promptoven.authservice.application.port.in.usecase;

import com.promptoven.authservice.application.port.in.dto.EmailCheckRequestDTO;
import com.promptoven.authservice.application.port.in.dto.EmailRequestRequestDTO;

public interface MediaAuthUseCase {

	boolean checkMedia(EmailCheckRequestDTO emailCheckRequestDTO);

	void requestEmail(EmailRequestRequestDTO emailRequestRequestDTO);

	void requestPhone(String phone);

}
