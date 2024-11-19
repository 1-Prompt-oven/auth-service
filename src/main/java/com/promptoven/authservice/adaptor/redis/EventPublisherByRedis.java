package com.promptoven.authservice.adaptor.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.out.call.EventPublisher;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventPublisherByRedis implements EventPublisher {

	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public void publish(String topic, Object message) {
		redisTemplate.opsForValue().set(topic, message.toString());
	}

}
