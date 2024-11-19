package com.promptoven.authservice.adaptor.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.dto.MemberNicknameUpdateRequestEvent;
import com.promptoven.authservice.application.port.in.dto.PromoteToSellerRequestDTO;
import com.promptoven.authservice.application.port.in.dto.UpdateNicknameRequestDTO;
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
	public void listen(String memberUUID) {
		memberManagementProxy.promoteToSeller(PromoteToSellerRequestDTO.builder()
			.memberUUID(memberUUID)
			.build());
	}

	@KafkaListener(
		topics = "${profile-nickname-update-request-event}",
		containerFactory = "memberNicknameUpdateFactory"
	)
	public void listenProfileNicknameUpdateRequest(MemberNicknameUpdateRequestEvent event) {
		log.info("Received nickname update event: {}", event);
		UpdateNicknameRequestDTO updateNicknameRequestDTO = UpdateNicknameRequestDTO.builder()
			.memberUUID(event.getMemberUuid())
			.nickname(event.getNickname())
			.build();
		memberManagementProxy.updateNickname(updateNicknameRequestDTO);
	}
}