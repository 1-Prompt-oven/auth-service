package com.promptoven.authservice.adaptor.web.controller.vo.in;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OauthLoginRequestVO {
    private String provider;
    private String providerID;
    private @Nullable String email;
}
