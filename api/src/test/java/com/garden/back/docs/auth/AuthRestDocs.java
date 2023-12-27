package com.garden.back.docs.auth;

import com.garden.back.auth.AuthController;
import com.garden.back.auth.AuthService;
import com.garden.back.auth.jwt.response.RefreshTokenResponse;
import com.garden.back.auth.jwt.response.TokenResponse;
import com.garden.back.docs.RestDocsSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

class AuthRestDocs extends RestDocsSupport {

    AuthService authService = mock(AuthService.class);

    @Override
    protected Object initController() {
        return new AuthController(authService);
    }

    @DisplayName("kakao 로그인 api docs")
    @Test
    void loginWithKakao() throws Exception {
        TokenResponse response = sut.giveMeBuilder(TokenResponse.class)
            .set("grantType", "Bearer")
            .set("accessToken", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW1zazMxMTNAbmF2ZXIuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MDM1OTQzMDZ9.SZw1YMw2aZ4AjA8hBQL9phMRTWFGZek5c8mvKDIkMI")
            .set("refreshToken", "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDQ4MDM5MDZ9.Ev551IJTI2hJ380ZVRXc8O3VLXbhfbB4AIe3lV0re7s")
            .set("accessTokenExpiredDate", 1703594306493L)
            .set("refreshTokenExpiredDate", 1703594306493L)
            .sample();

        given(authService.login(any(), any())).willReturn(response);

        mockMvc.perform(post("/v1/auth/kakao")
                .header("Authorization", "Bearer f-YtRrCTiBdnRY7gflPzk0TYf6MmCSXb1boKPXUZAAABjKCdJFZONYg--5I0Sw")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(document("login-with-kakao",
                requestHeaders(
                    headerWithName("Authorization").description("카카오에서 받은 엑세스 토큰(이 양식대로 보내주셔야 합니다.)")
                ),
                responseFields(
                    fieldWithPath("grantType").description("인증 토큰 타입 (Bearer)"),
                    fieldWithPath("accessToken").description("엑세스 토큰"),
                    fieldWithPath("refreshToken").description("리프레시 토큰"),
                    fieldWithPath("accessTokenExpiredDate").description("엑세스 토큰 만료 날짜 (밀리초 단위 타임스탬프)"),
                    fieldWithPath("refreshTokenExpiredDate").description("리프레시 토큰 만료 날짜 (밀리초 단위 타임스탬프)")
                )
            ));
    }

    @DisplayName("리프레시 토큰으로 access token을 발급 받는 api docs")
    @Test
    void generateAccessTokenWithRefreshToken() throws Exception {
        RefreshTokenResponse response = sut.giveMeBuilder(RefreshTokenResponse.class)
            .set("accessToken", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW1zazMxMTNAbmF2ZXIuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MDM1OTQzMDZ9.SZw1YMw2aZ4AjA8hBQL9phMRTWFGZek5c8mvKDIkMI")
            .sample();

        given(authService.generateAccessToken(any())).willReturn(response);

        mockMvc.perform(post("/v1/auth/refresh")
                .header("Refresh", "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDQ4MjQxMzZ9.5qJZqWtuqF6BaGtpbJmchaettS3buzUAtHIrkvDP06E")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(document("refresh-access-token",
                requestHeaders(
                    headerWithName("Refresh").description("리프레시 토큰")
                ),
                responseFields(
                    fieldWithPath("accessToken").description("엑세스 토큰")
                )
            ));
    }

    @DisplayName("네이버 로그인 api docs")
    @Test
    void loginWithNaver() throws Exception {
        TokenResponse response = sut.giveMeBuilder(TokenResponse.class)
            .set("grantType", "Bearer")
            .set("accessToken", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW1zazMxMTNAbmF2ZXIuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MDM1OTQzMDZ9.SZw1YMw2aZ4AjA8hBQL9phMRTWFGZek5c8mvKDIkMI")
            .set("refreshToken", "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDQ4MDM5MDZ9.Ev551IJTI2hJ380ZVRXc8O3VLXbhfbB4AIe3lV0re7s")
            .set("accessTokenExpiredDate", 1703594306493L)
            .set("refreshTokenExpiredDate", 1703594306493L)
            .sample();

        given(authService.login(any(), any())).willReturn(response);

        mockMvc.perform(post("/v1/auth/naver")
                .header("Authorization", "Bearer f-YtRrCTiBdnRY7gflPzk0TYf6MmCSXb1boKPXUZAAABjKCdJFZONYg--5I0Sw")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(document("login-with-naver",
                requestHeaders(
                    headerWithName("Authorization").description("네이버에서 받은 엑세스 토큰(이 양식대로 보내주셔야 합니다.)")
                ),
                responseFields(
                    fieldWithPath("grantType").description("인증 토큰 타입 (Bearer)"),
                    fieldWithPath("accessToken").description("엑세스 토큰"),
                    fieldWithPath("refreshToken").description("리프레시 토큰"),
                    fieldWithPath("accessTokenExpiredDate").description("엑세스 토큰 만료 날짜 (밀리초 단위 타임스탬프)"),
                    fieldWithPath("refreshTokenExpiredDate").description("리프레시 토큰 만료 날짜 (밀리초 단위 타임스탬프)")
                )
            ));
    }
}
