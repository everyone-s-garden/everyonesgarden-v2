package com.garden.back.auth.client.kakao;

import com.garden.back.member.Member;
import com.garden.back.auth.client.MemberProvider;
import com.garden.back.auth.client.kakao.response.KakaoOauth2Response;
import org.springframework.stereotype.Component;

@Component
public class KakaoMemberProvider implements MemberProvider {

    private final KakaoOAuthClient kakaoClient;

    public KakaoMemberProvider(KakaoOAuthClient kakaoClient) {
        this.kakaoClient = kakaoClient;
    }

    @Override
    public Member getMember(String authorization) {
        KakaoOauth2Response response = kakaoClient.getUserInfoFromKakao(authorization);
        return response.toEntity();
    }
}
