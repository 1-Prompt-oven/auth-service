package com.promptoven.authservice.adaptor.web.controller.vo.in;

import com.promptoven.authservice.application.port.in.dto.UpdateNicknameRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNicknameRequestVO {
	private String memberUUID;
	private String nickname;

	public UpdateNicknameRequestDTO toDTO() {
		return UpdateNicknameRequestDTO.builder()
			.memberUUID(memberUUID)
			.nickname(nickname)
			.build();
	}
}
