package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.CheckPWRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckPWRequestVO {

	private String memberUUID;
	private String password;

	public CheckPWRequestDTO toDTO() {
		return CheckPWRequestDTO.builder()
			.memberUUID(memberUUID)
			.password(password)
			.build();
	}

}
