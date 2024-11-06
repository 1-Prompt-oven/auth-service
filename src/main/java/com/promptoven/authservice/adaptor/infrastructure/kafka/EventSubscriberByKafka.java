package com.promptoven.authservice.adaptor.infrastructure.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.service.aop.MemberManagementProxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventSubscriberByKafka {

	private final MemberManagementProxy memberManagementProxy;

	@KafkaListener(topics = "first-Settlement-Registered")
	public void listen(String memberUuid) {
		memberManagementProxy.promoteToSeller(memberUuid);
	}

}
