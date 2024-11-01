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
public class FindMemberAspect {
	private final MemberPersistence memberPersistence;

	@Around("@annotation(findMemberOperation)")
	public Object findMemberAndInject(ProceedingJoinPoint joinPoint, FindMemberOperation findMemberOperation) throws
		Throwable {
		Object[] args = joinPoint.getArgs();
		MethodSignature signature = (MethodSignature)joinPoint.getSignature();
		String[] parameterNames = signature.getParameterNames();

		// Step 1: Find UUID parameter and get Member
		String uuid = null;
		for (int i = 0; i < parameterNames.length; i++) {
			if (parameterNames[i].equals(findMemberOperation.parameterName())) {
				uuid = (String)args[i];
				break;
			}
		}

		if (uuid == null) {
			throw new IllegalArgumentException("Member UUID not found in parameters");
		}

		Member member = memberPersistence.findByUuid(uuid);

		// Step 2: Inject member into arguments
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null && signature.getParameterTypes()[i].equals(Member.class)) {
				args[i] = member;
			}
		}

		// Step 3: Proceed with business logic
		return joinPoint.proceed(args);
	}
}