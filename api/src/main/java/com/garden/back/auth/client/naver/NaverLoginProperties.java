package com.garden.back.auth.client.naver;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class NaverLoginProperties {

    private final String clientId;
    private final String clientSecret;
    private final String grantType;
    private final String state;

    public NaverLoginProperties(
            @Value("${oauth2.naver.client-id:#{null}}") String clientId,
            @Value("${oauth2.naver.client-secret:#{null}}") String clientSecret,
            @Value("${oauth2.naver.grant-type:authorization_code}") String grantType,
            @Value("${oauth2.naver.state:RAMDOM_STATE}") String state
    ) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.state = state;
    }
}
