package com.promptoven.authservice.application.port.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoginUseCase {
	private String email;
	private String password;
}
