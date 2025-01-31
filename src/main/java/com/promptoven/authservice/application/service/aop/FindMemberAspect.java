package com.promptoven.authservice.application.service.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.promptoven.authservice.application.port.in.dto.MemberUUIDOnlyDTO;
import com.promptoven.authservice.application.port.in.usecase.MemberManagementUseCase;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.application.service.dto.MemberDTO;
import com.promptoven.authservice.application.service.dto.mapper.MemberDomainDTOMapper;
import com.promptoven.authservice.domain.Member;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class FindMemberAspect {

	private final MemberPersistence memberPersistence;
	private final MemberDomainDTOMapper memberDomainDTOMapper;

	@Around("@annotation(findMemberOperation)")
	public Object findMemberAndInject(ProceedingJoinPoint joinPoint, FindMemberOperation findMemberOperation) throws
		Throwable {
		// Get method information
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		String methodName = signature.getMethod().getName();

		// Get the DTO from arguments
		Object[] args = joinPoint.getArgs();
		Object dto = args[0];

		// Extract memberUUID from DTO using reflection
		Method getUuidMethod = dto.getClass().getMethod("memberUUID");
		String memberUUID = (String)getUuidMethod.invoke(dto);

		// Find member
		MemberDTO memberDTO = memberPersistence.findByUuid(memberUUID);
		if (memberDTO == null) {
			throw new RuntimeException("Member not found with UUID: " + memberUUID);
		}

		// Get the proxy instance
		MemberManagementProxy proxy = (MemberManagementProxy)joinPoint.getTarget();
		MemberManagementUseCase service = proxy.getMemberManagementUseCase();

		// If DTO only contains memberUUID, invoke service method with just Member
		if (dto instanceof MemberUUIDOnlyDTO) {
			Method serviceMethod = service.getClass().getMethod(methodName, Member.class);
			return serviceMethod.invoke(service, memberDomainDTOMapper.toDomain(memberDTO));
		}

		// Otherwise invoke with both Member and DTO
		Method serviceMethod = service.getClass().getMethod(methodName, Member.class, dto.getClass());
		return serviceMethod.invoke(service, memberDomainDTOMapper.toDomain(memberDTO), dto);
	}
}