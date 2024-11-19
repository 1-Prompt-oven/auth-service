package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.UnbanRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UnbanRequestVO {

	private String memberUUID;

	public UnbanRequestDTO toDTO() {
		return UnbanRequestDTO.builder()
			.memberUUID(memberUUID)
			.build();
	}

}
