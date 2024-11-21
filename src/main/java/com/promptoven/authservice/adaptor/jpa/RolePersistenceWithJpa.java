package com.promptoven.authservice.adaptor.jpa;

import java.util.List;

import org.springframework.stereotype.Service;

import com.promptoven.authservice.adaptor.jpa.entity.RoleEntity;
import com.promptoven.authservice.adaptor.jpa.repository.RoleRepository;
import com.promptoven.authservice.application.port.out.call.RolePersistence;
import com.promptoven.authservice.application.service.dto.RoleDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class RolePersistenceWithJpa implements RolePersistence {

	private final RoleRepository roleRepository;

	@Override
	public void create(RoleDTO role) {
		RoleEntity roleEntity = JpaRoleDTOEntityMapper.toEntity(role);
		roleRepository.save(roleEntity);
	}

	@Override
	public RoleDTO findRoleById(int roleID) {
		RoleEntity roleEntity = roleRepository.findById(roleID).orElse(new RoleEntity());
		return JpaRoleDTOEntityMapper.toDTO(roleEntity);
	}

	@Override
	public RoleDTO findByName(String roleName) {
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
	public void updateRole(RoleDTO updatedRole) {
		roleRepository.save(JpaRoleDTOEntityMapper.toEntity(updatedRole));
	}

	@Override
	public long count() {
		return roleRepository.count();
	}

	@Override
	public List<RoleDTO> findAll() {
		return roleRepository.findAll().stream()
			.map(JpaRoleDTOEntityMapper::toDTO)
			.toList();
	}
}
