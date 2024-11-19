package com.promptoven.authservice.adaptor.redis;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.out.call.AuthTaskMemory;

import lombok.RequiredArgsConstructor;

@Service
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisAuthTaskMemory implements AuthTaskMemory {

	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public boolean isTokenBlocked(String token) {
		return Boolean.TRUE.equals(redisTemplate.hasKey(token));
	}

	@Override
	public void blockToken(String token, Date expires) {
		long expirationTime = expires.getTime() - System.currentTimeMillis();
		redisTemplate.opsForValue().set(token, "1", expirationTime, TimeUnit.MILLISECONDS);
	}

	@Override
	public void recordAuthChallenge(String media, String code, Date expires) {
		long expirationTime = expires.getTime() - System.currentTimeMillis();
		redisTemplate.opsForValue().set(media, code, expirationTime, TimeUnit.MILLISECONDS);
	}

	@Override
	public String getAuthChallenge(String media) {
		String value = redisTemplate.opsForValue().get(media);
		return null != value ? value : "";
	}

	@Override
	public void recordAuthChallengeSuccess(String media, Date expires) {
		long expirationTime = expires.getTime() - System.currentTimeMillis();
		redisTemplate.opsForValue().set(media, "Success", expirationTime, TimeUnit.MILLISECONDS);
	}
}
