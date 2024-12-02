package com.promptoven.authservice.adaptor.web.controller.vo.out;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SocialLoginInfoResponseVO {

	private String provider;
	private String providerID;

}
