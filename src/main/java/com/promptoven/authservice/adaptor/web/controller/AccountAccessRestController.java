package com.promptoven.authservice.adaptor.web.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.promptoven.authservice.adaptor.web.util.BaseResponse;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.CheckPWRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.LoginRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.reqeust.ResetPWRequestMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.response.LoginResponseMapper;
import com.promptoven.authservice.adaptor.web.controller.mapper.response.RefreshResponseMapper;
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
public class AccountAccessRestController {

	final String AuthHeader = "Authorization";
	final String RefreshHeader = "Refreshtoken";

	private final AccountAccessUsecase accountAccessUsecase;
	private final MemberManagementUseCase memberManagementUseCase;

	@PostMapping("/login")
	public BaseResponse<LoginResponseVO> login(@RequestBody LoginRequestVO loginRequestVO) {
		LoginResponseDTO loginResponseDTO = accountAccessUsecase.login(LoginRequestMapper.toDTO(loginRequestVO));
		return new BaseResponse<>(LoginResponseMapper.fromDTO(loginResponseDTO));
	}

	@PostMapping("/logout")
	public BaseResponse<Void> logout(@RequestHeader(AuthHeader) String accessToken,
		@RequestHeader(RefreshHeader) String refreshToken) {
			accountAccessUsecase.logout(accessToken, refreshToken);
			return new BaseResponse<>();
	}

	@DeleteMapping("/withdraw")
	public BaseResponse<Void> withdraw(@RequestHeader(AuthHeader) String accessToken) {
		accountAccessUsecase.withdraw(accessToken);
		return new BaseResponse<>();
	}

	@PostMapping("/resetPW")
	public BaseResponse<Void> resetPW(@RequestBody ResetPWRequestVO resetPWRequestVO) {
		memberManagementUseCase.resetPW(ResetPWRequestMapper.toDTO(resetPWRequestVO));
		return new BaseResponse<>();
	}

	@PostMapping("/checkPW")
	public BaseResponse<Boolean> checkPW(@RequestBody CheckPWRequestVO checkPWRequestVO) {
		return new BaseResponse<>(accountAccessUsecase.checkPW(CheckPWRequestMapper.toDTO(checkPWRequestVO)));
	}

	@GetMapping("/refresh")
	public BaseResponse<RefreshResponseVO> tokenUpdate(@RequestHeader(RefreshHeader) String refreshToken) {
		return new BaseResponse<>(RefreshResponseMapper.fromDTO(accountAccessUsecase.refresh(refreshToken)));
	}

}
