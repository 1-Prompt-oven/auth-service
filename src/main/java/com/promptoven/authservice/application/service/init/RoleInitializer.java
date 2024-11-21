package com.promptoven.authservice.application.service.init;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.service.dto.RoleDTO;
import com.promptoven.authservice.application.service.dto.mapper.RoleDomainDTOMapper;
import com.promptoven.authservice.domain.Role;
import com.promptoven.authservice.domain.dto.RoleModelDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class RoleInitializer implements ApplicationListener<ApplicationReadyEvent> {

	private final RolePersistence rolePersistence;
	private final RoleDomainDTOMapper roleDomainDTOMapper;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		initializeRoleData();
	}

	private void initializeRoleData() {

		if (rolePersistence.count() == 0) {
			RoleBaseEntityList().forEach(rolePersistence::create);
			log.info("Role data is empty. Initializing...");

		} else {
			log.info("Role data is already initialized.");
		}
	}

	private List<RoleDTO> RoleBaseEntityList() {
		RoleModelDTO memberModel = RoleModelDTO.builder()
			.id(1)
			.name("member")
			.description("ROLE_MEMBER")
			.build();
		RoleModelDTO sellerModel = RoleModelDTO.builder()
			.id(2)
			.name("seller")
			.description("ROLE_SELLER")
			.build();
		RoleModelDTO adminModel = RoleModelDTO.builder()
			.id(3)
			.name("admin")
			.description("ROLE_ADMIN")
			.build();

		return List.of(
			roleDomainDTOMapper.toDTO(Role.createRole(memberModel)),
			roleDomainDTOMapper.toDTO(Role.createRole(sellerModel)),
			roleDomainDTOMapper.toDTO(Role.createRole(adminModel))
		);
	}
}
