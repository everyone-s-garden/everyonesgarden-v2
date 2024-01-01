package com.graden.back.auth.client.naver.response;

import com.garden.back.member.Member;
import com.garden.back.member.Role;

public record NaverOauth2Response(
    String resultcode,
    String message,
    MemberInfo response
) {
    public Member toEntity() {
        return Member.create(response.email, response.name, Role.USER);
    }

    public record MemberInfo(
        String id,
        String email,
        String name
    ) {}
}
