package com.garden.back.auth.client.kakao;

import com.garden.back.auth.client.AuthRequest;
import com.garden.back.auth.client.MemberProvider;
import com.garden.back.auth.client.kakao.response.KakaoOauth2Response;
import com.garden.back.auth.client.kakao.response.KakaoTokenResponse;
import com.garden.back.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KakaoMemberProvider implements MemberProvider {

    private final KakaoOAuthClient kakaoClient;
    private final KakaoOAuthClient.KakaoTokenClient kakaoTokenClient;
    private final KakaoLoginProperties properties;

    public KakaoMemberProvider(
            KakaoOAuthClient kakaoClient,
            KakaoOAuthClient.KakaoTokenClient kakaoTokenClient,
            KakaoLoginProperties properties
    ) {
        this.kakaoClient = kakaoClient;
        this.kakaoTokenClient = kakaoTokenClient;
        this.properties = properties;
    }

    @Override
    public Member getMember(AuthRequest requestDto) {
        KakaoTokenResponse tokenResponse = kakaoTokenClient.getToken(new KakaoTokenRequest(
                properties.getGrantType(),
                properties.getClientId(),
                requestDto.redirectUri(),
                requestDto.code()));
        KakaoOauth2Response response = kakaoClient.getUserInfoFromKakao("Bearer " + tokenResponse.access_token());
        return response.toEntity();
    }
}
