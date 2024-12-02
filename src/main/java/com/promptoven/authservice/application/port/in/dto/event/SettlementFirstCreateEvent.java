package com.promptoven.authservice.application.port.in.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class SettlementFirstCreateEvent {

	private String memberUUID;

}
