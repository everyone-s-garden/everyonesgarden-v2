package com.garden.back.service;

import com.garden.back.auth.AuthProvider;
import com.garden.back.auth.client.MemberProvider;
import com.garden.back.auth.client.kakao.KakaoMemberProvider;
import com.garden.back.auth.client.kakao.KakaoOAuthClient;
import com.garden.back.auth.client.naver.NaverMemberProvider;
import com.garden.back.auth.client.naver.NaverOAuthClient;
import com.garden.back.auth.config.SecurityConfig;
import com.garden.back.auth.jwt.JwtProperties;
import com.garden.back.auth.jwt.TokenProvider;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

@ActiveProfiles("test")
@SpringBootTest
//@Import(TestSecurityConfig.class)
public class IntegrationTestSupport {

     @MockBean
     protected TokenProvider tokenProvider;

     @MockBean
     protected Map<AuthProvider, MemberProvider> authRegistrations;

     @MockBean
     protected KakaoMemberProvider kakaoMemberProvider;

     @MockBean
     protected NaverMemberProvider naverMemberProvider;

     @MockBean
     protected SecurityConfig securityConfig;

     @MockBean
     NaverOAuthClient naverOAuthClient;

     @MockBean
     KakaoOAuthClient kakaoOAuthClient;

    @MockBean
    JwtProperties jwtProperties;


}
