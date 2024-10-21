package com.promptoven.authservice.application.port.out.call;

import com.promptoven.authservice.domain.Member;

public interface MemberPersistence {

	void create(Member member);

	Member findByEmail(String email);

	Member findByUuid(String uuid);

}
