package com.promptoven.authservice.adaptor.web.controller.vo.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckPWRequestVO {

	private String memberUUID;
	private String password;

	public String getMemberUUID() {
		return memberUUID;
	}

	public String getPassword() {
		return password;
	}

}
