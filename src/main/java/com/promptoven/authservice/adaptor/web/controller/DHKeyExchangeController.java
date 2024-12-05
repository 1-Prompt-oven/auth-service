package com.promptoven.authservice.adaptor.web.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.adaptor.web.util.BaseResponse;
import com.promptoven.authservice.application.port.in.DHkeyExchangeUsecase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/v2/auth/key-exchange")
@RequiredArgsConstructor
public class DHKeyExchangeController {
	private final DHkeyExchangeUsecase dHkeyExchangeUsecase;

	@PostMapping("/init")
	public BaseResponse<String> initializeKeyExchange(
		@RequestHeader("X-Session-ID") String sessionId) throws Exception {
		String serverPublicKey = dHkeyExchangeUsecase.initializeKeyExchange(sessionId);
		return new BaseResponse<>(serverPublicKey);
	}

	@PostMapping("/complete")
	public BaseResponse<Void> completeKeyExchange(
		@RequestHeader("X-Session-ID") String sessionId,
		@RequestBody String clientPublicKey) throws Exception {
		dHkeyExchangeUsecase.completeKeyExchange(sessionId, clientPublicKey);
		return new BaseResponse<>();
	}

	@PostMapping("/destroy")
	public BaseResponse<Void> terminateDHKeySession(
		@RequestHeader("X-Session-ID") String sessionId) throws Exception {
		dHkeyExchangeUsecase.cleanupSession(sessionId);
		return new BaseResponse<>();
	}
}