package com.promptoven.authservice.adaptor.web.controller.mapper.response;

import com.promptoven.authservice.adaptor.web.controller.vo.out.RefreshResponseVO;
import com.promptoven.authservice.application.port.out.dto.RefreshDTO;

public class RefreshResponseMapper {

	public static RefreshResponseVO fromDTO(RefreshDTO dto) {
		return RefreshResponseVO.builder()
			.accessToken(dto.getAccessToken())
			.nickname(dto.getNickname())
			.role(dto.getRole())
			.build();
	}
}
