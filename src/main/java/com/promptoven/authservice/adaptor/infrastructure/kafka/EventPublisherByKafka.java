package com.promptoven.authservice.adaptor.infrastructure.kafka;

import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.out.call.EventPublisher;

import lombok.RequiredArgsConstructor;

@Primary
@Service
@RequiredArgsConstructor
public class EventPublisherByKafka implements EventPublisher {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void publish(String topic, Object message) {
		kafkaTemplate.send(topic, message);
	}

}
