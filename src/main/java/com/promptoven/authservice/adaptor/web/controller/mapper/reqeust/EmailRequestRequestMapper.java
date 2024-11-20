package com.promptoven.authservice.adaptor.web.controller.mapper.reqeust;

import com.promptoven.authservice.adaptor.web.controller.vo.in.EmailRequestRequestVO;
import com.promptoven.authservice.application.port.in.dto.EmailRequestRequestDTO;

public class EmailRequestRequestMapper {

	public static EmailRequestRequestDTO toDTO(EmailRequestRequestVO vo) {
		return new EmailRequestRequestDTO(vo.getEmail());
	}

}