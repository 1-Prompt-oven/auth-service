package com.promptoven.authservice.application.port.out.call;

import java.util.Date;

import com.promptoven.authservice.application.port.out.dto.AuthChallengeDTO;

public interface AuthTaskMemory {

	boolean isTokenBlocked(String token);

	void blockToken(String token, Date expires);

	void recordAuthChallenge(AuthChallengeDTO authChallenge);

	String getAuthChallenge(String media);

	boolean isAuthChallengeSuccess(String media);

	void recordAuthChallengeSuccess(String media, Date expires);
}
