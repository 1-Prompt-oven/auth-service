package com.promptoven.authservice.adaptor.infrastructure;

import com.promptoven.authservice.domain.Member;

public interface AuthPersistence {

	void create(Member member);

	Member findByEmail(String email);

	Member findByUuid(String uuid);

}
