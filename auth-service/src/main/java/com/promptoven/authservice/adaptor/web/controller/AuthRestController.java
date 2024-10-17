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

    @PostMapping(`"/login")
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

    @PostMapping("/register")
    public LoginResponseVO register(@RequestBody RegisterRequestVO registerRequestVO) {
        log.info("register: {}", registerRequestVO);
        return new LoginResponseVO();
    }

    @PostMapping("/oauth/register")
    public void oauthRegister(@RequestBody OauthRegisterRequestVO oauthRegisterRequestVO) {
        log.info("oauth register: {}", oauthRegisterRequestVO);
    }

    @PostMapping("/oauth/unregister")
    public void oauthUnregister(@RequestBody OauthUnregisterRequestVO oauthUnregisterRequestVO) {
        log.info("oauth unregister: {}", oauthUnregisterRequestVO);
    }

    @PostMapping("/withdraw")
    public void withdraw(@RequestHeader("Authorization") String authorizationHeader) {
        String accessToken = authorizationHeader.replace("Bearer ", "");
        log.info("withdraw token: {}", accessToken);
    }

    @PostMapping("/resetPW")
    public void resetPW(@RequestBody ResetPWRequestVO resetPWRequestVO) {
        log.info("reset password: {}", resetPWRequestVO);
    }

    @PostMapping("/changePW")
    public void changePW(@RequestBody ChangePWRequestVO changePWRequestVO) {
        log.info("change password: {}", changePWRequestVO);
    }

    @PostMapping("/email/reqeust")
    public void emailRequest(@RequestBody EmailRequestRequestVO emailRequestRequestVO) {
        log.info("email request: {}", emailRequestRequestVO);
    }

    @PostMapping("/email/check")
    public boolean emailCheck(@RequestBody EmailCheckRequestVO emailCheckRequestVO) {
        log.info("email check: {}", emailCheckRequestVO);
        return true;
    }

    @PostMapping("/verify/email")
    public boolean verifyEmail(@RequestBody VerifyEmailRequestVO verifyEmailRequestVO) {
        log.info("verify email: {}", verifyEmailRequestVO);
        return true;
    }

    @PostMapping("/verify/phone")
    public boolean verifyPhone(@RequestBody VerifyPhoneRequestVO verifyPhoneRequestVO) {
        log.info("verify phone: {}", verifyPhoneRequestVO);
        return true;
    }

    @PostMapping("/verify/nickname")
    public boolean verifyNickname(@RequestBody VerifyNicknameRequestVO verifyNicknameRequestVO) {
        log.info("verify nickname: {}", verifyNicknameRequestVO);
        return true;
    }

    @PostMapping("/register-social")
    public LoginResponseVO registerSocial(@RequestBody RegisterSocialRequestVO registerSocialRequestVO) {
        log.info("register social: {}", registerSocialRequestVO);
        return new LoginResponseVO();
    }
}
