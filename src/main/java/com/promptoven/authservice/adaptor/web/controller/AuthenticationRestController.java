package com.promptoven.authservice.adaptor.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.adaptor.web.controller.vo.in.CheckPWRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.LoginRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.ResetPWRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.out.LoginResponseVO;
import com.promptoven.authservice.adaptor.web.controller.vo.out.RefreshResponseVO;
import com.promptoven.authservice.application.port.in.usecase.AuthenticationUseCase;
import com.promptoven.authservice.application.port.out.dto.LoginResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthenticationRestController {

	final String AuthHeader = "Authorization";
	final String RefreshHeader = "Refreshtoken";

	private final AuthenticationUseCase authenticationUseCase;

	@PostMapping("/login")
	public LoginResponseVO login(@RequestBody LoginRequestVO loginRequestVO) {
		LoginResponseDTO loginResponseDTO = authenticationUseCase.login(loginRequestVO.toDTO());
		return LoginResponseVO.from(loginResponseDTO);
	}

	@PostMapping("/logout")
	public void logout(@RequestHeader(AuthHeader) String accessToken,
		@RequestHeader(RefreshHeader) String refreshToken) {
		authenticationUseCase.logout(accessToken.replace("Bearer ", ""), refreshToken.replace("Bearer ", ""));
	}

	@PostMapping("/withdraw")
	public void withdraw(@RequestHeader(AuthHeader) String authorizationHeader) {
		String accessToken = authorizationHeader.replace("Bearer ", "");
		authenticationUseCase.withdraw(accessToken);
	}

	//todo: dto 사용으로 수정
	@PostMapping("/resetPW")
	public void resetPW(@RequestBody ResetPWRequestVO resetPWRequestVO) {
		authenticationUseCase.resetPW(resetPWRequestVO.toDTO());
	}

	//todo: dto 사용으로 수정
	@PostMapping("/checkPW")
	public boolean checkPW(@RequestBody CheckPWRequestVO checkPWRequestVO) {
		return authenticationUseCase.checkPW(checkPWRequestVO.toDTO());
	}

	@GetMapping("/refresh")
	public RefreshResponseVO tokenUpdate(@RequestHeader(RefreshHeader) String refreshToken) {
		return RefreshResponseVO.from(authenticationUseCase.refresh(refreshToken.replace("Bearer ", "")));
	}

}
