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

			String accessToken = jwtProvider.issueJwt(member.getUuid());
			String refreshToken = jwtProvider.issueRefresh(accessToken);

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
	public void logout(String AccessToken, String RefreshToken) {
		authTaskMemory.blockToken(AccessToken, jwtProvider.getTokenExpiration(AccessToken));
		authTaskMemory.blockToken(RefreshToken, jwtProvider.getTokenExpiration(RefreshToken));
	}

	@Override
	public void resetPW(String email, String password) {

		Member member = memberPersistence.findByEmail(email);

		memberPersistence.updatePassword(Member.updateMemberPassword(member, passwordEncoder.encode(password)));
	}

	@Override
	public void withdraw(String accessToken) {

		String extractedMemberUUID = jwtProvider.getClaimOfToken(accessToken, "sub");
		memberPersistence.remove(Member.deleteMember(
				memberPersistence.findByUuid(extractedMemberUUID)
			)
		);
		eventPublisher.publish(memberWithdrawEvent, extractedMemberUUID);
	}

	@Override
	public RefreshDTO refresh(String refreshToken) {
		String accessToken = jwtProvider.refreshByToken(refreshToken);
		String memberUUID = jwtProvider.getClaimOfToken(accessToken, "sub");
		Member member = memberPersistence.findByUuid(memberUUID);
		String nickname = member.getNickname();
		String role = rolePersistence.findRoleById(member.getRole()).getName();
		return RefreshDTO.builder()
			.accessToken(accessToken)
			.nickname(nickname)
			.role(role)
			.build();
	}
}
