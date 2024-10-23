package com.promptoven.authservice.application.service.springsecurity;

import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@Primary
public class OauthAuthenticationProvider implements AuthenticationProvider {

	private final OauthUserDetailService oauthUserDetailService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String memberUID = authentication.getName();
		log.info("member uuid: {}", memberUID);

		AuthUserDetail authUserDetail = (AuthUserDetail)oauthUserDetailService.loadUserByUsername(memberUID);
		return new UsernamePasswordAuthenticationToken(authUserDetail, null, authUserDetail.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

}