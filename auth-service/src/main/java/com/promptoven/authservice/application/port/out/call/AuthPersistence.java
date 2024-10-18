package com.promptoven.authservice.application.port.out.call;

import com.promptoven.authservice.domain.Member;

public interface AuthPersistence {

	void create(Member member);

	Member findByEmail(String email);

	Member findByUuid(String uuid);

}
