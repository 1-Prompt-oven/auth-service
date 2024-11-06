package com.promptoven.authservice.adaptor.infrastructure.redis;

import org.springframework.data.redis.core.RedisTemplate;

import com.promptoven.authservice.application.port.out.call.EventPublisher;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EventPublisherByRedis implements EventPublisher {

	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public void publish(String topic, Object message) {
		redisTemplate.opsForValue().set(topic, message.toString());
	}

	@Override
	public void publish(String topic, String message) {
		redisTemplate.opsForValue().set(topic, message);
	}
}
