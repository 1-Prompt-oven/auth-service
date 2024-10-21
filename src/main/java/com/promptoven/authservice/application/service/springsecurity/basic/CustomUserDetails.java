package com.promptoven.authservice.application.service.springsecurity.basic;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.Role;

public class CustomUserDetails implements UserDetails {

	Member member;
	String role;

	public CustomUserDetails(Member member, Role role) {
		this.member = member;
		this.role = role.getName();
	}

	@Override
	public String getUsername() {
		return member.getUuid();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority Role = new GrantedAuthority() {
			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				return CustomUserDetails.this.role;
			}
		};
		return List.of(Role);
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
