package com.promptoven.authservice.application.service.springsecurity;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.domain.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthUserDetailService implements UserDetailsService {

	private final MemberPersistence memberPersistence;
	private final RolePersistence rolePersistence;

	@Override
	public UserDetails loadUserByUsername(String uuid)
		throws UsernameNotFoundException {
		Member member = memberPersistence.findByUuid(uuid);
		return new AuthUserDetail(member,
			rolePersistence.findRoleById(member.getRole()));
	}

}