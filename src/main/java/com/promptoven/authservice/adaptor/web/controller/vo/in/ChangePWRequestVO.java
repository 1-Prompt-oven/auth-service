package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.ChangePWRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePWRequestVO {
	
	private String newPassword;
	private String memberUUID;

	public ChangePWRequestDTO toDTO() {
		return ChangePWRequestDTO.builder()
			.newPassword(newPassword)
			.memberUUID(memberUUID)
			.build();
	}

}
