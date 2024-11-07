package com.promptoven.authservice.adaptor.web.controller.vo.out;

import com.promptoven.authservice.application.port.out.dto.RefreshDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class RefreshResponseVO {

	private String accessToken;
	private String nickname;
	private String role;

	public static RefreshResponseVO from(RefreshDTO refreshDTO) {
		return RefreshResponseVO.builder()
			.accessToken(refreshDTO.getAccessToken())
			.nickname(refreshDTO.getNickname())
			.role(refreshDTO.getRole())
			.build();
	}
}
