package com.promptoven.authservice.adaptor.web.cors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ControllerAdvice
public class CorsVerifierAdvice {

	private final CorsVerifier corsVerifier;

	@ModelAttribute
	public void verifyCors(HttpServletRequest request) {
		if (!corsVerifier.isAllowedRequest(request)) {
			throw new CorsNotAllowedException("Disallowed Request");
		}
	}

	@ExceptionHandler(CorsNotAllowedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public void handleCorsNotAllowed() {
		// You can add logging or other handling logic here if needed
	}

	public static class CorsNotAllowedException extends RuntimeException {
		public CorsNotAllowedException(String message) {
			super(message);
		}
	}
}