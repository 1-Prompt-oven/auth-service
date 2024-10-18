package com.promptoven.authservice.application.service.springsecurity.basic;

import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.usecase.ChangePWUseCase;

@Service("authServiceBySpringSecurity")
public class AuthServiceImpl implements ChangePWUseCase {

	@Override
	public void changePW(String oldPassword, String newPassword) {
		// TODO Auto-generated method stub
		System.out.println("ChangePWUseCase");
	}
}
