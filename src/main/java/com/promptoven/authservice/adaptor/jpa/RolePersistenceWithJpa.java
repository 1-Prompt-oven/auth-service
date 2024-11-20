package com.promptoven.authservice.adaptor.jpa;

import org.springframework.stereotype.Service;

import com.promptoven.authservice.adaptor.jpa.entity.RoleEntity;
import com.promptoven.authservice.adaptor.jpa.repository.RoleRepository;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.domain.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RolePersistenceWithJpa implements RolePersistence {

	private final RoleRepository roleRepository;

	@Override
	public void create(Role role) {
		RoleEntity roleEntity = JpaRoleDTOEntityMapper.toEntity(role);
		roleRepository.save(roleEntity);
	}

	@Override
	public Role findRoleById(int roleID) {
		RoleEntity roleEntity = roleRepository.findById(roleID).orElse(new RoleEntity());
		return JpaRoleDTOEntityMapper.toDTO(roleEntity);
	}

	@Override
	public Role findByName(String roleName) {
		RoleEntity roleEntity = roleRepository.findByName(roleName);
		return null != roleEntity ? JpaRoleDTOEntityMapper.toDTO(roleEntity) : null;
	}

	@Override
	public int findMaxRoleID() {
		return roleRepository.findMaxRoleID();
	}

	@Override
	public void deleteRoleById(int roleID) {
		roleRepository.deleteById(roleID);
	}

	@Override
	public void updateRole(Role updatedRole) {
		roleRepository.save(JpaRoleDTOEntityMapper.toEntity(updatedRole));
	}

	@Override
	public long count() {
		return roleRepository.count();
	}
}
