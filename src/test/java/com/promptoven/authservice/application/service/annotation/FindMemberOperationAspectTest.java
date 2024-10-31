package com.promptoven.authservice.application.service.annotation;

import static org.mockito.Mockito.*;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.promptoven.authservice.application.port.out.call.MemberPersistence;
import com.promptoven.authservice.domain.Member;

@ExtendWith(MockitoExtension.class)
class FindMemberOperationAspectTest {

	@Mock
	private MemberPersistence memberPersistence;

	@Mock
	private ProceedingJoinPoint joinPoint;

	@Mock
	private MethodSignature methodSignature;

	@InjectMocks
	private FindMemberOperationAspect findMemberOperationAspect;

	@BeforeEach
	void setUp() {
		when(joinPoint.getSignature()).thenReturn(methodSignature);
	}

	@Test
	void testConvertUUIDToMember() throws Throwable {
		String memberUUID = "e6f8e3c4-0ffc-4cfa-ae19-4236f50b9873";
		Member member = mock(Member.class);
		Object[] args = {memberUUID};
		String[] parameterNames = {"memberUUID"};

		when(methodSignature.getParameterNames()).thenReturn(parameterNames);
		when(joinPoint.getArgs()).thenReturn(args);
		when(memberPersistence.findByUuid(memberUUID)).thenReturn(member);

		findMemberOperationAspect.convertUUIDToMember(joinPoint);

		Object[] expectedArgs = {memberUUID, member};
		verify(joinPoint).proceed(expectedArgs);
	}
}