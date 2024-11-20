package com.promptoven.authservice.application.service.dto.mapper;

public interface DomainDTOMapper<Domain, DTO> {
	Domain toDomain(DTO dto);

	DTO toDTO(Domain domain);
}
