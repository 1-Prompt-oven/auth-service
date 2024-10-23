package com.promptoven.authservice.application.service.springsecurity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.Role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Slf4j
@Getter
@NoArgsConstructor
public class AuthUserDetail implements UserDetails {

	private String uuid;
	private String password;
	private String role;
	private Boolean isDeleted;
	private String nickname;
	private Boolean isBanned;

	public AuthUserDetail(Member member, Role role) {
		this.uuid = member.getUuid();
		this.password = member.getPassword();
		this.isDeleted = member.getIsDeleted();
		this.nickname = member.getNickname();
		this.role = role.getName();
		this.isBanned = member.getIsBanned();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(() -> this.role);
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.uuid;
	}

	public String getNickname() {
		return this.nickname;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !this.isDeleted;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !this.isBanned;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !(this.password.equals("clear"));
	}

	@Override
	public boolean isEnabled() {
		return !(this.isDeleted || this.isBanned);
	}
}