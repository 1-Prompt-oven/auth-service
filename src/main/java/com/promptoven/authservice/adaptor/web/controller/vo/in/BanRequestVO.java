package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.BanRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BanRequestVO {

	private String memberUUID;

	public BanRequestDTO toDTO() {
		return BanRequestDTO.builder()
			.memberUUID(memberUUID)
			.build();
	}
}
