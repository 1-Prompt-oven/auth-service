package com.promptoven.authservice.adaptor.web.controller.vo.out;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class LoginResponseVO {

	private String accesstoken;
	private String refreshtoken;
	private String nickname;
	private String role;
	private String memberUUID;

}
