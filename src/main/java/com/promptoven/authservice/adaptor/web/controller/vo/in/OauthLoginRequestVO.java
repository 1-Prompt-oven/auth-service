package com.promptoven.authservice.adaptor.web.controller.vo.in;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OauthLoginRequestVO {
	private String provider;
	private String providerID;
	private @Nullable String email;
}
