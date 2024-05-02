package com.garden.back.auth.client.naver;

import com.garden.back.auth.client.AuthRequest;
import com.garden.back.auth.client.MemberProvider;
import com.garden.back.auth.client.naver.response.NaverOauth2Response;
import com.garden.back.auth.client.naver.response.NaverTokenResponse;
import com.garden.back.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NaverMemberProvider implements MemberProvider {

    private final NaverOAuthClient naverClient;
    private final NaverOAuthClient.NaverTokenClient naverTokenClient;
    private final NaverLoginProperties properties;
    public NaverMemberProvider(
            NaverOAuthClient naverClient,
            NaverOAuthClient.NaverTokenClient naverTokenClient,
            NaverLoginProperties properties
    ) {
        this.naverClient = naverClient;
        this.naverTokenClient = naverTokenClient;
        this.properties = properties;
    }

    @Override
    public Member getMember(AuthRequest authRequest) {
        NaverTokenResponse tokenResponse = naverTokenClient.getToken(new NaverTokenRequest(
                properties.getGrantType(),
                properties.getClientId(),
                        properties.getClientSecret(),
                authRequest.redirectUri(),
                authRequest.code()));
        log.info("tokenResponse: {}", tokenResponse);
        NaverOauth2Response response = naverClient.getUserInfoFromNaver(tokenResponse.access_token());
        return response.toEntity();
    }
}
