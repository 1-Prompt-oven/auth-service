package com.promptoven.authservice.adaptor.web.controller.vo.out;

import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
public class SocialLoginResponseVO {

	private @Nullable String accesstoken;
	private @Nullable String refreshtoken;
	private @Nullable String nickname;
	private @Nullable String role;
	private @Nullable String memberUUID;
	private boolean failed;

}
