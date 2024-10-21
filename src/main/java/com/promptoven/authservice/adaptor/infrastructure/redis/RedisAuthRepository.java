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

import com.promptoven.authservice.application.port.out.call.AuthRepository;

@Service
@EnableRedisRepositories
public class RedisAuthRepository implements AuthRepository {

	@Value("${spring.data.redis.host}")
	private String host;

	@Value("${spring.data.redis.port}")
	private int port;

	public RedisConnectionFactory redisConnectionFactory() {
		RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
		redisConfiguration.setHostName(host);
		redisConfiguration.setPort(port);
		return new LettuceConnectionFactory(redisConfiguration);
	}

	public RedisTemplate<String, String> redisTemplate() {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		return redisTemplate;
	}

	public boolean isTokenBlocked(String token) {
		RedisTemplate<String, String> redisTemplate = redisTemplate();
		return redisTemplate.hasKey(token);
	}

	public void blockToken(String token, Date expires) {
		RedisTemplate<String, String> redisTemplate = redisTemplate();
		Date now = new Date();
		redisTemplate.opsForValue().set(token, "1", expires.getTime() - now.getTime(), TimeUnit.MILLISECONDS);
	}

	public void recordAuthChallenge(String media, String code, Date expires) {
		RedisTemplate<String, String> redisTemplate = redisTemplate();
		redisTemplate.opsForValue().set(media, code,
			expires.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

	public String getAuthChallenge(String media) {
		RedisTemplate<String, String> redisTemplate = redisTemplate();
		return redisTemplate.opsForValue().get(media);
	}

	public void recordAuthChallengeSuccess(String media, Date expires) {
		RedisTemplate<String, String> redisTemplate = redisTemplate();
		redisTemplate.opsForValue().set(media, "Success",
			expires.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}
}
