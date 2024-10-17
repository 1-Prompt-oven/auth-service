package com.promptoven.authservice.adaptor.infrastructure.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthTaskEntity {

	public String media;
	public String challengeCode;

}
