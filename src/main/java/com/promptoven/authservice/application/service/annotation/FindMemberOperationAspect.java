package com.promptoven.authservice.application.service.annotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.domain.Member;

import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class FindMemberOperationAspect {

	private final MemberPersistence memberPersistence;

	@Around("@annotation(findMemberOperation)")
	public Object convertUUIDToMember(ProceedingJoinPoint joinPoint, FindMemberOperation findMemberOperation) throws Throwable {
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		Object[] args = joinPoint.getArgs();
		
		// Find the UUID parameter
		String memberUUID = null;
		for (int i = 0; i < args.length; i++) {
			if (args[i] instanceof String && methodSignature.getParameterNames()[i].contains("memberUUID")) {
				memberUUID = (String) args[i];
				Member member = memberPersistence.findByUuid(memberUUID);
				args[i] = member;
				break;
			}
		}
		
		return joinPoint.proceed(args);
	}
} 