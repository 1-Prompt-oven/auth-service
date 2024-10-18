package com.promptoven.authservice.application.service.springsecurity.basic;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.promptoven.authservice.domain.Member;

public class CustomUserDetails implements UserDetails {

	Member member;

	public CustomUserDetails(Member member) {
		this.member = member;
	}

	@Override
	public String getUsername() {
		return member.getUuid();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of();
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return !member.getIsDeleted();
	}

	@Override
	public boolean isAccountNonLocked() {
		return !member.getIsBanned();
	}

	@Override
	public boolean isEnabled() {
		return !member.getIsBanned() && !member.getIsDeleted();
	}

}
