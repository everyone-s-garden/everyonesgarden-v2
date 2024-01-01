package com.graden.back.auth.client.naver;

import com.garden.back.member.Member;
import com.graden.back.auth.client.MemberProvider;
import com.graden.back.auth.client.naver.response.NaverOauth2Response;
import org.springframework.stereotype.Component;

@Component
public class NaverMemberProvider implements MemberProvider {

    private final NaverOAuthClient naverClient;

    public NaverMemberProvider(NaverOAuthClient naverClient) {
        this.naverClient = naverClient;
    }

    @Override
    public Member getMember(String authorization) {
        NaverOauth2Response response = naverClient.getUserInfoFromKakao(authorization);
        return response.toEntity();
    }
}
