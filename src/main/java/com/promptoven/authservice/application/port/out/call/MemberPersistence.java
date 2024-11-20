package com.promptoven.authservice.application.port.out.call;

import com.promptoven.authservice.application.service.dto.MemberDTO;

public interface MemberPersistence {

	void create(MemberDTO memberDTO);

	MemberDTO findByEmail(String email);

	MemberDTO findByUuid(String uuid);

	MemberDTO findByNickname(String nickname);

	boolean existsByEmail(String email);

	boolean existsByNickname(String nickname);

	void updatePassword(MemberDTO updatedMember);

	void remove(MemberDTO memberDTO);

	void updateMember(MemberDTO updatedMember);

}
