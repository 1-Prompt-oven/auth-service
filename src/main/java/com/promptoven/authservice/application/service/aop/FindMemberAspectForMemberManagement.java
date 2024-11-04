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
public class FindMemberAspectForMemberManagement {
	private final MemberPersistence memberPersistence;

	@Around("@annotation(findMemberOperation)")
	public Object findMemberAndInject(ProceedingJoinPoint joinPoint, FindMemberOperation findMemberOperation) throws
		Throwable {
		// Get method information
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		String methodName = signature.getMethod().getName();

		// Find member UUID from arguments
		Object[] args = joinPoint.getArgs();
		String memberUUID = (String)args[0];

		// Find member
		Member member = memberPersistence.findByUuid(memberUUID);
		if (member == null) {
			throw new RuntimeException("Member not found with UUID: " + memberUUID);
		}

		// Get the proxy instance
		MemberManagementProxy proxy = (MemberManagementProxy)joinPoint.getTarget();
		MemberManagementUseCase service = proxy.getMemberManagementUseCase();

		// Invoke the service method directly with the correct parameters
		Method serviceMethod;
		if (args.length > 1) {
			serviceMethod = service.getClass().getMethod(methodName, Member.class, String.class);
		} else {
			serviceMethod = service.getClass().getMethod(methodName, Member.class);
		}

		if (args.length > 1) {
			return serviceMethod.invoke(service, member, args[1]);
		} else {
			return serviceMethod.invoke(service, member);
		}
	}
}