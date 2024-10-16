package com.promptoven.authservice.adaptor.web.controller;

import com.promptoven.authservice.adaptor.web.controller.vo.in.*;
import com.promptoven.authservice.adaptor.web.controller.vo.out.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthRestController {

    @PostMapping("/login")
    public LoginResponseVO login(@RequestBody LoginRequestVO loginRequestVO){
        return new LoginResponseVO();
    }

    @PostMapping("/oauth/login")
    public LoginResponseVO oauthLogin(@RequestBody OauthLoginRequestVO oauthLoginRequestVO){
        return new LoginResponseVO();
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        log.info("logout token: {}", accessToken);
    }
}
