package com.promptoven.authservice.application.port.out.call;

import java.util.Date;

public interface AuthRepository {

	boolean isTokenBlocked(String token);

	void blockToken(String token, Date expires);

	void recordAuthChallenge(String media, String code, Date expires);

	String getAuthChallenge(String media);

	void recordAuthChallengeSuccess(String media, Date expires);
}
