package com.promptoven.authservice.adaptor.infrastructure.jpa.entity;

import com.promptoven.authservice.domain.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@Table(name = "role")   
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;

    public static RoleEntity fromDomain(Role role) {
        return RoleEntity.builder()
            .id(role.getId())
            .name(role.getName())
            .description(role.getDescription())
            .build();
    }

    public Role toDomain() {
        return Role.builder()
            .id(this.id)
            .name(this.name)
            .description(this.description)
            .build();
    }
    
    
}
