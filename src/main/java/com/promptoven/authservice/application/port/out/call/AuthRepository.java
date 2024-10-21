package com.promptoven.authservice.application.port.out.call;

import java.util.Date;

//todo: application layer에서 port.out 에 대해서 이름을 좀 다시 고민해보세요. 이름이 왜 나뉘냐

public interface AuthRepository {

	boolean isTokenBlocked(String token);

	void blockToken(String token, Date expires);

	void recordAuthChallenge(String media, String code, Date expires);

	String getAuthChallenge(String media);

	void recordAuthChallengeSuccess(String media, Date expires);
}
