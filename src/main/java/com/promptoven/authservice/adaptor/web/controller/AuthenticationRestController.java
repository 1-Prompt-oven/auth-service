package com.promptoven.authservice.adaptor.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.adaptor.web.controller.vo.in.CheckPWRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.LoginRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.in.ResetPWRequestVO;
import com.promptoven.authservice.adaptor.web.controller.vo.out.LoginResponseVO;
import com.promptoven.authservice.adaptor.web.controller.vo.out.RefreshResponseVO;
import com.promptoven.authservice.application.port.in.usecase.AccountAccessUsecase;
import com.promptoven.authservice.application.port.in.usecase.MemberManagementUseCase;
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

	private final AccountAccessUsecase accountAccessUsecase;
	private final MemberManagementUseCase memberManagementUseCase;

	@PostMapping("/login")
	public LoginResponseVO login(@RequestBody LoginRequestVO loginRequestVO) {
		LoginResponseDTO loginResponseDTO = accountAccessUsecase.login(loginRequestVO.toDTO());
		return LoginResponseVO.from(loginResponseDTO);
	}

	@PostMapping("/logout")
	public void logout(@RequestHeader(AuthHeader) String accessToken,
		@RequestHeader(RefreshHeader) String refreshToken) {
		accountAccessUsecase.logout(accessToken, refreshToken);
	}

	@PutMapping("/withdraw")
	public void withdraw(@RequestHeader(AuthHeader) String accessToken) {
		accountAccessUsecase.withdraw(accessToken);
	}

	@PostMapping("/resetPW")
	public void resetPW(@RequestBody ResetPWRequestVO resetPWRequestVO) {
		memberManagementUseCase.resetPW(resetPWRequestVO.toDTO());
	}

	@PostMapping("/checkPW")
	public boolean checkPW(@RequestBody CheckPWRequestVO checkPWRequestVO) {
		return accountAccessUsecase.checkPW(checkPWRequestVO.toDTO());
	}

	@GetMapping("/refresh")
	public RefreshResponseVO tokenUpdate(@RequestHeader(RefreshHeader) String refreshToken) {
		return RefreshResponseVO.from(accountAccessUsecase.refresh(refreshToken));
	}

}
