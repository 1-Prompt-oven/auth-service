package com.promptoven.authservice.adaptor.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.service.aop.MemberManagementProxy;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class EventSubscriberByRedis implements MessageListener {

	private final MemberManagementProxy memberManagementProxy;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String memberUuid = new String(message.getBody());
		memberManagementProxy.promoteToSeller(memberUuid);
	}
}