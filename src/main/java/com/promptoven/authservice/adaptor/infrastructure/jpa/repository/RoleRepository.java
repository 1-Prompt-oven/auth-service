package com.promptoven.authservice.adaptor.infrastructure.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.promptoven.authservice.adaptor.infrastructure.jpa.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    
}

