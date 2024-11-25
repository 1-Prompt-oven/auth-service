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
@Configuration
@RequiredArgsConstructor
public class MemberTestInitializer implements ApplicationListener<ApplicationReadyEvent> {

	private final MemberPersistence memberPersistence;
	private final MemberDomainDTOMapper memberDomainDTOMapper;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		if (memberPersistence.count() > 100) {
			log.info("Test member Data already exists. Skipping initialization...");
			return;
		}
		initializeTestMemberData();
	}

	private void initializeTestMemberData() {
		long startTime = System.currentTimeMillis();
		log.info("Test member Data needed. Initializing...");
		MemberTestEntityList().forEach(memberPersistence::create);
		long endTime = System.currentTimeMillis();
		log.info("Execution time: {} ms", (endTime - startTime));
	}

	private List<String> nicknameList() {
		// Create a list of test nicknames
		return List.of("철수", "영희", "준수", "영수", "민수", "준석", "영석", "민석", "준호", "영호", "민호", "준우", "영우", "민우", "준영", "민영",
			"준희", "민희", "준철", "영철", "민철", "준민", "영민", "민준", "준준", "영준", "준표", "수영", "희재", "홍철", "영진", "지훈", "성민",
			"희성", "은선", "다솜", "다은", "다현", "다영", "다인", "다정", "다희", "다윤", "다원", "다연", "다경", "다솔", "다은", "다인", "다영",
			"민선", "민서", "민지", "민아", "민주", "민정", "민희", "강희", "선강", "선우", "신우", "예성", "성희", "희성", "희영", "희진", "희주",
			"은서", "은솜", "윤슬", "예슬", "다슬", "예솜", "예지", "예은", "예인", "예린", "예림", "예나", "예나",
			"Michale", "James", "Douglas", "Victoria", "Bear", "Lion", "Tiger", "Elephant", "Giraffe", "Kangaroo",
			"Koala", "Panda", "Penguin", "Polar Bear", "Rhino", "Zebra", "Ant", "Bee", "Butterfly", "Caterpillar",
			"Dragonfly", "Fly", "Grasshopper", "Ladybug", "Mosquito", "Moth", "Wasp", "Bull", "Cow", "Goat", "Horse",
			"Pig", "Sheep", "Chicken", "Duck", "Eagle", "Ostrich", "Owl", "Parrot", "Peacock", "Pelican", "Pigeon",
			"Rooster", "Seagull", "Sparrow", "Swan", "Turkey", "Vulture", "Woodpecker", "Beetle", "Centipede",
			"Cockroach", "Cricket", "Earwig", "Mantis", "Praying Mantis", "Scorpion", "Spider", "Tarantula", "Antelope",
			"Baboon", "Bat", "Beaver", "Bison", "Boar", "Buffalo", "Camel", "Cheetah", "Chimpanzee", "Cougar", "Coyote",
			"Deer", "Dingo", "Dog", "Dolphin", "Elk", "Fox", "Gazelle"
		);

	}

	private List<MemberDTO> MemberTestEntityList() {
		List<MemberDTO> memberList = new ArrayList<>();
		List<String> nicknames = nicknameList();
		for (int i = 0; i < 10000; i++) {
			String baseNickname = nicknames.get(i % nicknames.size());
			String email = baseNickname + i + "@test.test";
			String nickname = baseNickname + i;
			MemberModelDTO memberModelDTO = MemberModelDTO.builder()
				.uuid(UUID.randomUUID().toString())
				.email(email)
				.nickname(nickname)
				.password(email)
				.role(1)
				.build();
			MemberDTO memberDTO = memberDomainDTOMapper.toDTO(Member.createMember(memberModelDTO));
			memberList.add(memberDTO);
		}
		return memberList;
	}
}