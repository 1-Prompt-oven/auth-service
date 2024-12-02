package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.VerifyNicknameRequestVO;
import com.promptoven.authservice.application.port.in.dto.VerifyNicknameRequestDTO;

public class VerifyNicknameRequestMapper {

	public static VerifyNicknameRequestDTO toDTO(VerifyNicknameRequestVO vo) {
		return VerifyNicknameRequestDTO.builder()
			.nickname(vo.getNickname())
			.build();
	}
}
