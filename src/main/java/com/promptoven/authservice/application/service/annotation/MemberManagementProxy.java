package com.promptoven.authservice.application.service.annotation;

import com.promptoven.authservice.application.port.in.usecase.MemberManagementUseCase;
import com.promptoven.authservice.domain.Member;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class MemberManagementProxy{

    private final MemberManagementUseCase memberManagementUseCase;
    private String memberUUID;
    private Member member;


    public MemberManagementProxy(@Autowired MemberManagementUseCase memberManagementUseCase) {
        this.memberManagementUseCase = memberManagementUseCase;
    }

    public void banMember(String memberUUID) {
        memberManagementUseCase.banMember(memberUUID, null);
    }

    public void unbanMember(String memberUUID) {
        memberManagementUseCase.unbanMember(memberUUID, null);
    }

    public void updateNickname(String memberUUID, String nickname) {
        memberManagementUseCase.updateNickname(memberUUID, nickname, null);
    }

    public void setMemberRole(String memberUUID, String roleName) {
        memberManagementUseCase.setMemberRole(memberUUID, roleName, null);
    }

    public void clearPassword(String memberUUID) {
        memberManagementUseCase.clearPassword(memberUUID, null);
    }
}