package com.promptoven.authservice.adaptor.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.promptoven.authservice.adaptor.jpa.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

	RoleEntity findByName(String roleName);

	@Query("SELECT MAX(r.id) FROM RoleEntity r")
	int findMaxRoleID();
}

