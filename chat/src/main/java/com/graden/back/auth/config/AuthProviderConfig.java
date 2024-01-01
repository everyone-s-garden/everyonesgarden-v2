package com.graden.back.auth.config;

import com.graden.back.auth.AuthProvider;
import com.graden.back.auth.client.MemberProvider;
import com.graden.back.auth.client.kakao.KakaoMemberProvider;
import com.graden.back.auth.client.naver.NaverMemberProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class AuthProviderConfig {

    @Bean
    public Map<AuthProvider, MemberProvider> authRegistrations(
        KakaoMemberProvider kakaoMemberProvider,
        NaverMemberProvider naverMemberProvider
    ) {
        Map<AuthProvider, MemberProvider> enumMap = new EnumMap<>(AuthProvider.class);
        enumMap.put(AuthProvider.KAKAO, kakaoMemberProvider);
        enumMap.put(AuthProvider.NAVER, naverMemberProvider);
        return enumMap;
    }
}
