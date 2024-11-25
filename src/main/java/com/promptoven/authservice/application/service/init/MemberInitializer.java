package com.promptoven.authservice.application.service.init;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.service.dto.MemberDTO;
import com.promptoven.authservice.application.service.dto.mapper.MemberDomainDTOMapper;
import com.promptoven.authservice.domain.Member;
import com.promptoven.authservice.domain.dto.MemberModelDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class MemberInitializer implements ApplicationListener<ApplicationReadyEvent> {

	private final MemberPersistence memberPersistence;
	private final MemberDomainDTOMapper memberDomainDTOMapper;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		if (memberPersistence.count() > 0) {
			log.info("member Data already exists. Skipping initialization...");
		} else {
			initializeMemberData();
		}
	}

	private void initializeMemberData() {
		long startTime = System.currentTimeMillis();
		log.info("Member Data is empty. Initializing...");
		MemberEntityList().forEach(memberPersistence::create);
		long endTime = System.currentTimeMillis();
		log.info("Execution time: {} ms", (endTime - startTime));
	}

	private List<MemberDTO> MemberEntityList() {
		List<MemberDTO> memberList = new ArrayList<>();
		MemberModelDTO adminMemberModelDTO = MemberModelDTO.builder()
			.uuid(UUID.randomUUID().toString())
			.email("administrator")
			.nickname("Service Root Admin")
			.password("Administrator@PromptOven.Shop")
			.role(3)
			.build();
		MemberDTO adminMemberDTO = memberDomainDTOMapper.toDTO(Member.createMember(adminMemberModelDTO));
		memberList.add(adminMemberDTO);
		MemberModelDTO testMemberModelDTO = MemberModelDTO.builder()
			.uuid(UUID.randomUUID().toString())
			.email("test")
			.nickname("Test User")
			.password("test")
			.role(1)
			.build();
		MemberDTO testMemberDTO = memberDomainDTOMapper.toDTO(Member.createMember(testMemberModelDTO));
		memberList.add(testMemberDTO);
		return memberList;
	}

}
