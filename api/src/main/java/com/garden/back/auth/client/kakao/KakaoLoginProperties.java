package com.garden.back.auth.client.kakao;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class KakaoLoginProperties {

    private final String clientId;
    private final String grantType;

    public KakaoLoginProperties(
            @Value("${oauth2.kakao.client-id}") String clientId,
            @Value("${oauth2.kakao.grant-type:authorization_code}") String grantType
    ) {
        this.clientId = clientId;
        this.grantType = grantType;
    }
}
