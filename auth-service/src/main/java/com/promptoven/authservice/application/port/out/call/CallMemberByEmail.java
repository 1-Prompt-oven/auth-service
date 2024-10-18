package com.promptoven.authservice.application.port.out.call;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.promptoven.authservice.adaptor.infrastructure.AuthPersistence;
import com.promptoven.authservice.domain.Member;

import lombok.Getter;

@Getter
@Component
public class CallMemberByEmail {

	@Autowired
	private final AuthPersistence authpersistence;

	public Member getMember(String email) {
		return authpersistence.findByEmail(email);
	}
}
