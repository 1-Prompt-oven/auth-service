package com.promptoven.authservice.application.service.init;

import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.service.dto.RoleDTO;
import com.promptoven.authservice.application.service.dto.mapper.RoleDomainDTOMapper;
import com.promptoven.authservice.domain.Role;

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
		return List.of(
			roleDomainDTOMapper.toDTO(Role.createRole("user", 1, "ROLE_USER")),
			roleDomainDTOMapper.toDTO(Role.createRole("seller", 2, "ROLE_SELLER")),
			roleDomainDTOMapper.toDTO(Role.createRole("admin", 3, "ROLE_ADMIN"))
		);
	}
}
