package com.promptoven.authservice.adaptor.web.controller.vo.out;

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

}
