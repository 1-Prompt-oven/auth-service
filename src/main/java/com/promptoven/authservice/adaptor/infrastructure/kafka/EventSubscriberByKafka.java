package com.promptoven.authservice.adaptor.infrastructure.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.dto.MemberNicknameUpdateRequestEvent;
import com.promptoven.authservice.application.service.aop.MemberManagementProxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventSubscriberByKafka {

	private final MemberManagementProxy memberManagementProxy;

	@KafkaListener(
		topics = "${settlement-first-create-event}",
		containerFactory = "stringKafkaListenerContainerFactory"
	)
	public void listen(String memberUuid) {
		memberManagementProxy.promoteToSeller(memberUuid);
	}

	@KafkaListener(
		topics = "${profile-nickname-update-request-event}",
		containerFactory = "memberNicknameUpdateFactory"
	)
	public void listenProfileNicknameUpdateRequest(MemberNicknameUpdateRequestEvent event) {
		log.info("Received nickname update event: {}", event);
		memberManagementProxy.updateNickname(event.getMemberUuid(), event.getNickname());
	}
}