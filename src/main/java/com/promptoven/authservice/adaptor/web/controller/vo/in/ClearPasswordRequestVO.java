package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.ClearPasswordRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClearPasswordRequestVO {

	private String memberUUID;

	public ClearPasswordRequestDTO toDTO() {
		return ClearPasswordRequestDTO.builder()
			.memberUUID(memberUUID)
			.build();
	}

}
