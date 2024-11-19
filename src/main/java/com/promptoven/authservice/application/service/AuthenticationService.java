package com.promptoven.authservice.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.dto.ChangePWRequestDTO;
import com.promptoven.authservice.application.port.in.dto.CheckPWRequestDTO;
import com.promptoven.authservice.application.port.in.dto.ResetPWRequestDTO;
import com.promptoven.authservice.application.port.in.usecase.AuthenticationUseCase;
import com.promptoven.authservice.application.port.out.call.AuthTaskMemory;
import com.promptoven.authservice.application.port.out.call.EventPublisher;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.port.out.dto.LoginResponseDTO;
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
	public boolean checkPW(CheckPWRequestDTO checkPWRequestDTO) {

		Member member = memberPersistence.findByUuid(checkPWRequestDTO.getMemberUUID());

		return passwordEncoder.matches(checkPWRequestDTO.getPassword(), member.getPassword());
	}

	@Override
	public void changePW(ChangePWRequestDTO changePWRequestDTO) {

		Member member = memberPersistence.findByUuid(changePWRequestDTO.getMemberUUID());
		String newPassword = changePWRequestDTO.getNewPassword();
		Member updatedMember = Member.updateMemberPassword(member, passwordEncoder.encode(newPassword));

		memberPersistence.updatePassword(updatedMember);
	}

	// 정책적인 이유로 login method는 DTO가 아니라 email과 PW 등을 string으로 바로 받아야 회원가입 method에서 여기 부르기 편함
	@Override
	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

		Member member = memberPersistence.findByEmail(loginRequestDTO.getEmail());

		if (null != member && passwordEncoder.matches(loginRequestDTO.getPassword(), member.getPassword())) {

			String role = rolePersistence.findRoleById(member.getRole()).getName();
			String memberUUID = member.getUuid();
			String accessToken = jwtProvider.issueJwt(memberUUID, role);
			String refreshToken = jwtProvider.issueRefresh(memberUUID);

			return LoginResponseDTO.builder()
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
		JwtProvider.TokenInfo accessTokenInfo = jwtProvider.decryptTokenOnly(accessToken.replace("Bearer ", ""));
		JwtProvider.TokenInfo refreshTokenInfo = jwtProvider.decryptTokenOnly(refreshToken.replace("Bearer ", ""));

		if (accessTokenInfo != null) {
			authTaskMemory.blockToken(accessToken, accessTokenInfo.getExpirationTime());
		}
		if (refreshTokenInfo != null) {
			authTaskMemory.blockToken(refreshToken, refreshTokenInfo.getExpirationTime());
		}
	}

	@Override
	public void resetPW(ResetPWRequestDTO resetPWRequestDTO) {

		Member member = memberPersistence.findByEmail(resetPWRequestDTO.getEmail());

		memberPersistence.updatePassword(
			Member.updateMemberPassword(member, passwordEncoder.encode(resetPWRequestDTO.getPassword())));
	}

	@Override
	public void withdraw(String accessToken) {
		JwtProvider.TokenInfo tokenInfo = jwtProvider.validateAndDecryptToken(accessToken.replace("Bearer ", ""));
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
		JwtProvider.TokenInfo tokenInfo = jwtProvider.validateAndDecryptToken(refreshToken.replace("Bearer ", ""));
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
