package com.promptoven.authservice.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.usecase.AuthenticationUseCase;
import com.promptoven.authservice.application.port.out.call.AuthTaskMemory;
import com.promptoven.authservice.application.port.out.call.EventPublisher;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.port.out.dto.LoginDTO;
import com.promptoven.authservice.application.port.out.dto.RefreshDTO;
import com.promptoven.authservice.application.service.utility.JwtProvider;
import com.promptoven.authservice.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationUseCase {

	private final MemberPersistence memberPersistence;
	private final RolePersistence rolePersistence;

	private final AuthTaskMemory authTaskMemory;

	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	private final EventPublisher eventPublisher;

	@Value("${member-withdraw-event}")
	private String memberWithdrawEvent;

	@Override
	public boolean checkPW(String password, String memberUUID) {

		Member member = memberPersistence.findByUuid(memberUUID);

		return passwordEncoder.matches(password, member.getPassword());
	}

	@Override
	public void changePW(String newPassword, String memberUUID) {

		Member member = memberPersistence.findByUuid(memberUUID);

		Member updatedMember = Member.updateMemberPassword(member, passwordEncoder.encode(newPassword));

		memberPersistence.updatePassword(updatedMember);
	}

	@Override
	public LoginDTO login(String email, String password) {

		Member member = memberPersistence.findByEmail(email);

		if (null != member && passwordEncoder.matches(password, member.getPassword())) {

			String role = rolePersistence.findRoleById(member.getRole()).getName();
			String memberUUID = member.getUuid();
			String accessToken = jwtProvider.issueJwt(memberUUID, role);
			String refreshToken = jwtProvider.issueRefresh(memberUUID);

			return LoginDTO.builder()
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.role(role)
				.uuid(member.getUuid())
				.nickname(member.getNickname())
				.build();
		}
		return null;
	}

	@Override
	public void logout(String accessToken, String refreshToken) {
		JwtProvider.TokenInfo accessTokenInfo = jwtProvider.decryptTokenOnly(accessToken);
		JwtProvider.TokenInfo refreshTokenInfo = jwtProvider.decryptTokenOnly(refreshToken);
		
		if (accessTokenInfo != null) {
			authTaskMemory.blockToken(accessToken, accessTokenInfo.getExpirationTime());
		}
		if (refreshTokenInfo != null) {
			authTaskMemory.blockToken(refreshToken, refreshTokenInfo.getExpirationTime());
		}
	}

	@Override
	public void resetPW(String email, String password) {

		Member member = memberPersistence.findByEmail(email);

		memberPersistence.updatePassword(Member.updateMemberPassword(member, passwordEncoder.encode(password)));
	}

	@Override
	public void withdraw(String accessToken) {
		JwtProvider.TokenInfo tokenInfo = jwtProvider.validateAndDecryptToken(accessToken);
		if (tokenInfo == null) {
			throw new RuntimeException("Invalid or expired token");
		}

		String memberUUID = tokenInfo.getUserId();
		memberPersistence.remove(Member.deleteMember(
			memberPersistence.findByUuid(memberUUID)
		));
		eventPublisher.publish(memberWithdrawEvent, memberUUID);
	}

	@Override
	public RefreshDTO refresh(String refreshToken) {
		JwtProvider.TokenInfo tokenInfo = jwtProvider.validateAndDecryptToken(refreshToken);
		if (tokenInfo == null) {
			throw new RuntimeException("Invalid or expired refresh token");
		}

		String memberUUID = tokenInfo.getUserId();
		Member member = memberPersistence.findByUuid(memberUUID);
		String nickname = member.getNickname();
		String role = rolePersistence.findRoleById(member.getRole()).getName();
		String accessToken = jwtProvider.issueJwt(memberUUID, role);
		
		return RefreshDTO.builder()
			.accessToken(accessToken)
			.nickname(nickname)
			.role(role)
			.build();
	}
}
