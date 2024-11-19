package com.promptoven.authservice.application.service.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.promptoven.authservice.application.port.in.usecase.MemberManagementUseCase;
import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.domain.Member;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class FindMemberAspect {
	private final MemberPersistence memberPersistence;

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
		Method getUuidMethod = dto.getClass().getMethod("getMemberUUID");
		String memberUUID = (String)getUuidMethod.invoke(dto);

		// Find member
		Member member = memberPersistence.findByUuid(memberUUID);
		if (member == null) {
			throw new RuntimeException("Member not found with UUID: " + memberUUID);
		}

		// Get the proxy instance
		MemberManagementProxy proxy = (MemberManagementProxy)joinPoint.getTarget();
		MemberManagementUseCase service = proxy.getMemberManagementUseCase();

		// Invoke the service method with member and DTO
		Method serviceMethod = service.getClass().getMethod(methodName, Member.class, dto.getClass());
		return serviceMethod.invoke(service, member, dto);
	}
}