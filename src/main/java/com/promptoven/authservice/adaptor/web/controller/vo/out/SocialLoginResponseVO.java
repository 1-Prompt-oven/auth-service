package com.promptoven.authservice.adaptor.web.controller.vo.out;

import org.springframework.lang.Nullable;

import com.promptoven.authservice.application.port.out.dto.SocialLoginDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class SocialLoginResponseVO {

	private @Nullable String accesstoken;
	private @Nullable String refreshtoken;
	private @Nullable String nickname;
	private @Nullable String role;
	private @Nullable String memberUUID;
	private boolean failed;

	public static SocialLoginResponseVO from(SocialLoginDTO socialLoginDTO) {
		return new SocialLoginResponseVO(socialLoginDTO.getAccessToken(), socialLoginDTO.getRefreshToken(),
			socialLoginDTO.getNickname(),
			socialLoginDTO.getRole(), socialLoginDTO.getUuid(), socialLoginDTO.isFailed());
	}

}
