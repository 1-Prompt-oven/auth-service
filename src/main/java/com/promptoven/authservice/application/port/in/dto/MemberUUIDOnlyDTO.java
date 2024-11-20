package com.promptoven.authservice.application.port.in.dto;

/**
 * Marker interface for DTOs that only contain memberUUID
 * in use for Aspect handle method with optimization
 */
public interface MemberUUIDOnlyDTO {
	String memberUUID();
} 