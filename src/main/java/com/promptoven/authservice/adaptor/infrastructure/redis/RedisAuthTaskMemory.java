package com.promptoven.authservice.adaptor.infrastructure.redis;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.out.call.AuthTaskMemory;

@Service
@EnableRedisRepositories
public class RedisAuthTaskMemory implements AuthTaskMemory {

	private final RedisTemplate<String, String> redisTemplate;

	public RedisAuthTaskMemory(@Value("${spring.data.redis.host}") String host,
		@Value("${spring.data.redis.port}") int port) {
		this.redisTemplate = createRedisTemplate(host, port);
	}

	private RedisTemplate<String, String> createRedisTemplate(String host, int port) {
		RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(host, port);
		RedisConnectionFactory connectionFactory = new LettuceConnectionFactory(redisConfiguration);

		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		template.setConnectionFactory(connectionFactory);
		template.afterPropertiesSet();
		return template;
	}

	@Override
	public boolean isTokenBlocked(String token) {

		return redisTemplate.hasKey(token);
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
		return redisTemplate.opsForValue().get(media);
	}

	@Override
	public void recordAuthChallengeSuccess(String media, Date expires) {
		long expirationTime = expires.getTime() - System.currentTimeMillis();
		redisTemplate.opsForValue().set(media, "Success", expirationTime, TimeUnit.MILLISECONDS);
	}
}
