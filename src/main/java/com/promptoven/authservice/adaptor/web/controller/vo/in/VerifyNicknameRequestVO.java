package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.VerifyNicknameRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VerifyNicknameRequestVO {

	private String nickname;
	
	public VerifyNicknameRequestDTO toDTO() {
		return VerifyNicknameRequestDTO.builder()
			.nickname(nickname)
			.build();
	}
	
}
