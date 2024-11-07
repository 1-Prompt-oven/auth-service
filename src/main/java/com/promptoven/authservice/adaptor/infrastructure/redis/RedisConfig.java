package com.promptoven.authservice.adaptor.infrastructure.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
	@Value("${spring.data.redis.host}")
	String host;
	@Value("${spring.data.redis.port}")
	int port;
	@Value("${settlement-first-create-event}")
	String firstSettlementRegisteredTopic;

	@Bean
	public RedisTemplate<String, String> redisTemplate() {
		return createRedisTemplate(host, port);
	}

	protected RedisTemplate<String, String> createRedisTemplate(String host, int port) {
		RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration(host, port);
		RedisConnectionFactory connectionFactory = new LettuceConnectionFactory(redisConfiguration);

		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(new StringRedisSerializer());
		template.setConnectionFactory(connectionFactory);
		template.afterPropertiesSet();
		return template;
	}

	@Bean
	RedisMessageListenerContainer container(MessageListenerAdapter listenerAdapter) {
		RedisConnectionFactory connectionFactory = new LettuceConnectionFactory(host, port);
		((LettuceConnectionFactory)connectionFactory).start();
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);

		if (!redisTemplate().hasKey(firstSettlementRegisteredTopic)) {
			redisTemplate().opsForValue().set(firstSettlementRegisteredTopic, "");
		}

		container.addMessageListener(listenerAdapter, new ChannelTopic(firstSettlementRegisteredTopic));
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(EventSubscriberByRedis subscriber) {
		return new MessageListenerAdapter(subscriber, "onMessage");
	}
}
