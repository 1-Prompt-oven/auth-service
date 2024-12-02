package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.ChangePWRequestVO;
import com.promptoven.authservice.application.port.in.dto.ChangePWRequestDTO;

public class ChangePWRequestMapper {

	public static ChangePWRequestDTO toDTO(ChangePWRequestVO vo) {
		return ChangePWRequestDTO.builder()
			.newPassword(vo.getNewPassword())
			.memberUUID(vo.getMemberUUID())
			.build();
	}

} 