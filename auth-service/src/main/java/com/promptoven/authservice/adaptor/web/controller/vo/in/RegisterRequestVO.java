package com.promptoven.authservice.adaptor.web.controller.vo.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestVO {

	private String email;
	private String password;
	private String nickname;

}
