package com.promptoven.authservice.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.promptoven.authservice.application.port.in.dto.CheckPWRequestDTO;
import com.promptoven.authservice.application.port.in.usecase.AccountAccessUsecase;
import com.promptoven.authservice.application.port.out.call.AuthTaskMemory;
import com.promptoven.authservice.application.port.out.call.EventPublisher;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.port.out.dto.LoginResponseDTO;
import com.promptoven.authservice.application.port.out.dto.MemberWithdrawEvent;
import com.promptoven.authservice.application.port.out.dto.RefreshDTO;
import com.promptoven.authservice.application.service.dto.LoginRequestDTO;
import com.promptoven.authservice.application.service.dto.MemberDTO;
import com.promptoven.authservice.application.service.dto.mapper.MemberDomainDTOMapper;
import com.promptoven.authservice.application.service.dto.mapper.RoleDomainDTOMapper;
import com.promptoven.authservice.application.service.utility.JwtProvider;
import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountAccessService implements AccountAccessUsecase {

	private final MemberDomainDTOMapper memberDomainDTOMapper;
	private final RoleDomainDTOMapper roleDomainDTOMapper;
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

		MemberDTO memberDTO = memberPersistence.findByUuid(checkPWRequestDTO.getMemberUUID());
		Member member = memberDomainDTOMapper.toDomain(memberDTO);

		return passwordEncoder.matches(checkPWRequestDTO.getPassword(), member.getPassword());
	}

	// 정책적인 이유로 login method는 DTO가 아니라 email과 PW 등을 string으로 바로 받아야 회원가입 method에서 여기 부르기 편함
	@Override
	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {

		MemberDTO memberDTO = memberPersistence.findByEmail(loginRequestDTO.getEmail());
		Member member = memberDomainDTOMapper.toDomain(memberDTO);

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
	public void withdraw(String accessToken) {
		JwtProvider.TokenInfo tokenInfo = jwtProvider.validateAndDecryptToken(accessToken.replace("Bearer ", ""));
		if (tokenInfo == null) {
			throw new RuntimeException("Invalid or expired token");
		}
		authTaskMemory.blockToken(accessToken.replace("Bearer ", ""), tokenInfo.getExpirationTime());
		String memberUUID = tokenInfo.getUserId();
		memberPersistence.remove(memberDomainDTOMapper.toDTO(
			Member.deleteMember(memberDomainDTOMapper.toDomain(memberPersistence.findByUuid(memberUUID)))));
		eventPublisher.publish(memberWithdrawEvent, new MemberWithdrawEvent(memberUUID));
	}

	@Override
	public RefreshDTO refresh(String refreshToken) {
		JwtProvider.TokenInfo tokenInfo = jwtProvider.validateAndDecryptToken(refreshToken.replace("Bearer ", ""));
		if (tokenInfo == null) {
			throw new RuntimeException("Invalid or expired refresh token");
		}

		String memberUUID = tokenInfo.getUserId();
		MemberDTO memberDTO = memberPersistence.findByUuid(memberUUID);
		Member member = memberDomainDTOMapper.toDomain(memberDTO);
		String nickname = member.getNickname();
		Role roleDomain = roleDomainDTOMapper.toDomain(rolePersistence.findRoleById(member.getRole()));
		String role = roleDomain.getName();
		String accessToken = jwtProvider.issueJwt(memberUUID, role);

		return RefreshDTO.builder()
			.accessToken(accessToken)
			.nickname(nickname)
			.role(role)
			.build();
	}
}
