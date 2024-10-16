package com.promptoven.authservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class Member {

    private String uuid;

    private String email;
    private String password;
    private String nickname;

    private LocalDateTime createdAt;
    private Boolean isDeleted;
    private Boolean isBanned;

    private int role;

    public static Member createMember(
            String uuid, String email, String password, String nickname, LocalDateTime createdAt, int role)
    {
        return Member.builder()
                .uuid(uuid)
                .email(email)
                .password(password)
                .nickname(nickname)
                .createdAt(LocalDateTime.now())
                .isDeleted(true)
                .isBanned(true)
                .role(role)
                .build();
    }

}
