package com.garden.back.auth.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "oauth2.jwt")
@Component
@Setter
@Getter
public class JwtProperties {
    private int accessTokenExpireTime;
    private int refreshTokenExpireTime;
    private String authorityKey;
    private String bearerPrefix;
    private String tokenSecret;
    private String accessTokenHeader;

    public int getAccessTokenExpireTime() {
        return dayToMilliSec(accessTokenExpireTime);
    }

    public int getRefreshTokenExpireTime() {
        return dayToMilliSec(refreshTokenExpireTime);
    }

    private int dayToMilliSec(int day) {
        return 1000 * 60 * 60 * 24 * day;
    }
}
