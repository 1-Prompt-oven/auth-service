// package com.promptoven.authservice.application.service;
//
// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;
//
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
//
// import com.promptoven.authservice.application.port.out.call.MemberPersistence;
// import com.promptoven.authservice.application.port.out.call.RolePersistence;
// import com.promptoven.authservice.domain.Member;
// import com.promptoven.authservice.domain.Role;
//
// class MemberManagementServiceTest {
//
// 	@Mock
// 	private MemberPersistence memberPersistence;
//
// 	@Mock
// 	private RolePersistence rolePersistence;
//
// 	@InjectMocks
// 	private MemberManagementService memberManagementService;
//
// 	@BeforeEach
// 	void setUp() {
// 		MockitoAnnotations.openMocks(this);
// 	}
//
// 	@Test
// 	void promoteToSeller() {
// 		String memberUUID = "test-uuid";
// 		Member member = mock(Member.class);
// 		Member updatedMember = mock(Member.class);
//
// 		when(member.getUuid()).thenReturn(memberUUID);
// 		when(updatedMember.getUuid()).thenReturn(memberUUID);
// 		when(member.getRole()).thenReturn(1);
// 		// when(Member.updateMemberRole(member, 2)).thenReturn(updatedMember);
//
// 		memberManagementService.promoteToSeller(memberUUID);
//
// 		assertEquals(member.getUuid(), updatedMember.getUuid());
// 		verify(memberPersistence).updateMember(updatedMember);
// 	}
//
// 	@Test
// 	void setMemberRole() {
// 		String memberUUID = "test-uuid";
// 		String roleName = "ROLE_USER";
// 		Role role = Role.createRole(roleName, 1, "");
// 		Member member = mock(Member.class);
//
// 		when(rolePersistence.findByName(roleName)).thenReturn(role);
// 		when(memberPersistence.findByUuid(memberUUID)).thenReturn(member);
//
// 		memberManagementService.setMemberRole(memberUUID, roleName);
//
// 		verify(memberPersistence).updateMember(Member.updateMemberRole(member, role.getId()));
// 	}
//
// 	@Test
// 	void banMember() {
// 		String memberUUID = "test-uuid";
// 		Member member = mock(Member.class);
//
// 		when(memberPersistence.findByUuid(memberUUID)).thenReturn(member);
//
// 		memberManagementService.banMember(memberUUID);
//
// 		verify(memberPersistence).updateMember(Member.banMember(member));
// 	}
//
// 	@Test
// 	void unbanMember() {
// 		String memberUUID = "test-uuid";
// 		Member member = mock(Member.class);
//
// 		when(memberPersistence.findByUuid(memberUUID)).thenReturn(member);
//
// 		memberManagementService.unbanMember(memberUUID);
//
// 		verify(memberPersistence).updateMember(Member.unbanMember(member));
// 	}
//
// 	@Test
// 	void updateNickname() {
// 		String memberUUID = "test-uuid";
// 		String nickname = "new-nickname";
// 		Member member = mock(Member.class);
//
// 		when(memberPersistence.findByUuid(memberUUID)).thenReturn(member);
//
// 		memberManagementService.updateNickname(memberUUID, nickname);
//
// 		verify(memberPersistence).updateMember(Member.updateMemberNickname(member, nickname));
// 	}
//
// 	@Test
// 	void clearPassword() {
// 		String memberUUID = "test-uuid";
// 		Member member = mock(Member.class);
//
// 		when(memberPersistence.findByUuid(memberUUID)).thenReturn(member);
//
// 		memberManagementService.clearPassword(memberUUID);
//
// 		verify(memberPersistence).updateMember(Member.updateMemberPassword(member, "clear"));
// 	}
// }