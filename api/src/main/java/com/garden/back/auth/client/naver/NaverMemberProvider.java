package com.garden.back.auth.client.naver;

import com.garden.back.auth.client.MemberProvider;
import com.garden.back.auth.client.naver.response.NaverOauth2Response;
import com.garden.back.member.Member;
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
