package com.promptoven.authservice.application.port.out.call;

public interface EventPublisher {

	void publish(String topic, Object message);

	void publish(String topic, String message);
}
