package com.garden.back.auth.config;

import com.garden.back.auth.AuthProvider;
import com.garden.back.auth.client.MemberProvider;
import com.garden.back.auth.client.kakao.KakaoMemberProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class AuthProviderConfig {

    @Bean
    public Map<AuthProvider, MemberProvider> authRegistrations(
        KakaoMemberProvider kakaoMemberProvider
    ) {
        Map<AuthProvider, MemberProvider> enumMap = new EnumMap<>(AuthProvider.class);
        enumMap.put(AuthProvider.KAKAO, kakaoMemberProvider);
        return enumMap;
    }
}
