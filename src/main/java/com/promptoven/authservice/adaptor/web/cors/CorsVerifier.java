package com.promptoven.authservice.adaptor.web.cors;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component
public class CorsVerifier {

	@Value("${cors.allowed-origins}")
	private final List<String> allowedOrigins;

	@Value("${cors.allowed-methods}")
	private final List<String> allowedMethods;

	public CorsVerifier(List<String> allowedOrigins, List<String> allowedMethods) {
		this.allowedOrigins = allowedOrigins;
		this.allowedMethods = allowedMethods;
	}

	public boolean isAllowedRequest(HttpServletRequest request) {
		String origin = request.getRequestURL().toString();
		String method = request.getMethod();
		return allowedOrigins.contains(origin) && allowedMethods.contains(method);
	}
}
