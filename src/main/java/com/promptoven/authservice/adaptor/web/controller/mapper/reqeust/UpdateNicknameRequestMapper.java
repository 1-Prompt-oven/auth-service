package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.UpdateNicknameRequestVO;
import com.promptoven.authservice.application.port.in.dto.UpdateNicknameRequestDTO;

public class UpdateNicknameRequestMapper {

	public static UpdateNicknameRequestDTO toDTO(UpdateNicknameRequestVO vo) {
		return UpdateNicknameRequestDTO.builder()
			.memberUUID(vo.getMemberUUID())
			.nickname(vo.getNickname())
			.build();
	}
}
