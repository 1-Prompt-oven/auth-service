package com.promptoven.authservice.adaptor.web.controller.vo.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OauthUnregisterRequestVO {

	private String provider;
	private String providerId;

}
