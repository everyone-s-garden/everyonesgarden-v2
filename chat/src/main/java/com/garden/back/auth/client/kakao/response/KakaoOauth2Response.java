package com.graden.back.auth.client.kakao.response;

import com.garden.back.member.Member;
import com.garden.back.member.Role;

import java.util.Map;

public record KakaoOauth2Response(
    long id,
    String connected_at,
    Map<String, String> properties,
    KakaoAccount kakao_account
) {
    public record KakaoAccount(
        boolean profile_nickname_needs_agreement,
        Profile profile,
        boolean has_email,
        boolean email_needs_agreement,
        boolean is_email_valid,
        boolean is_email_verified,
        String email
    ) {
        public record Profile(
            String nickname
        ) {
        }
    }
    public Member toEntity() {
        return Member.create(kakao_account().email, kakao_account.profile.nickname, Role.USER);
    }
}
