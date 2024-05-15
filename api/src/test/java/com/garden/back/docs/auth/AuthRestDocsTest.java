package com.garden.back.docs.auth;

import com.garden.back.auth.AuthController;
import com.garden.back.auth.AuthService;
import com.garden.back.auth.client.AuthRequest;
import com.garden.back.auth.jwt.response.TokenResponse;
import com.garden.back.docs.RestDocsSupport;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.cookies.CookieDocumentation.cookieWithName;
import static org.springframework.restdocs.cookies.CookieDocumentation.requestCookies;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthRestDocsTest extends RestDocsSupport {

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
                .set("accessTokenExpiredDate", 17035493L)
                .set("refreshTokenExpiredDate", 17035493L)
                .set("memberId", 1L)
                .sample();

        AuthRequest authRequest = sut.giveMeBuilder(AuthRequest.class)
                .set("code", "카카오로 받은 authorization 코드값")
                .set("redirectUri", "카카오에게 요청했던 redirect uri")
                .sample();
        given(authService.login(any(), any())).willReturn(response);

        mockMvc.perform(post("/v1/auth/kakao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest))
                )
                .andExpect(status().isOk())
                .andDo(document("login-with-kakao",
                        requestFields(
                                fieldWithPath("code").description("카카오로 받은 authorization 코드값"),
                                fieldWithPath("redirectUri").description("카카오에게 요청했던 redirect uri")
                        ),
                        responseFields(
                                fieldWithPath("grantType").description("인증 토큰 타입 (Bearer)"),
                                fieldWithPath("accessToken").description("엑세스 토큰"),
                                fieldWithPath("accessTokenExpiredDate").description("엑세스 토큰 만료 날짜 (밀리초 단위 타임스탬프)"),
                                fieldWithPath("refreshTokenExpiredDate").description("리프레시 토큰 만료 날짜 (밀리초 단위 타임스탬프)"),
                                fieldWithPath("memberId").description("로그인 한 사용자의 id")
                        ),
                        responseHeaders(
                                headerWithName("Set-Cookie").description("리프레시 토큰을 담은 쿠키")
                        )
                ));
    }

    @DisplayName("리프레시 토큰으로 access token, refresh token 을 발급 받는 api docs")
    @Test
    void generateAccessTokenWithRefreshToken() throws Exception {
        TokenResponse response = sut.giveMeBuilder(TokenResponse.class)
                .set("grantType", "Bearer")
                .set("accessToken", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraW1zazMxMTNAbmF2ZXIuY29tIiwiYXV0aCI6IlVTRVIiLCJleHAiOjE3MDM1OTQzMDZ9.SZw1YMw2aZ4AjA8hBQL9phMRTWFGZek5c8mvKDIkMI")
                .set("refreshToken", "eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3MDQ4MDM5MDZ9.Ev551IJTI2hJ380ZVRXc8O3VLXbhfbB4AIe3lV0re7s")
                .set("accessTokenExpiredDate", 17035493L)
                .set("refreshTokenExpiredDate", 17035493L)
                .set("memberId", 1L)
                .sample();

        given(authService.generateToken(any())).willReturn(response);

        Cookie cookie = new Cookie("refreshToken", response.refreshToken());

        mockMvc.perform(post("/v1/auth/refresh")
                        .cookie(cookie)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andDo(document("refresh-access-token",
                        requestCookies(
                                cookieWithName("refreshToken").description("리프레시 토큰")
                        ),
                        responseFields(
                                fieldWithPath("grantType").description("인증 토큰 타입 (Bearer)"),
                                fieldWithPath("accessToken").description("엑세스 토큰"),
                                fieldWithPath("accessTokenExpiredDate").description("엑세스 토큰 만료 날짜 (밀리초 단위 타임스탬프)"),
                                fieldWithPath("refreshTokenExpiredDate").description("리프레시 토큰 만료 날짜 (밀리초 단위 타임스탬프)"),
                                fieldWithPath("memberId").description("로그인 한 사용자의 id")
                        ),
                        responseHeaders(
                                headerWithName("Set-Cookie").description("리프레시 토큰을 담은 쿠키")
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
                .set("accessTokenExpiredDate", 17035493L)
                .set("refreshTokenExpiredDate", 17035493L)
                .set("memberId", 1L)
                .sample();

        AuthRequest authRequest = sut.giveMeBuilder(AuthRequest.class)
                .set("code", "네이버로 받은 authorization 코드값")
                .set("redirectUri", "네이버에 요청했던 redirect uri")
                .sample();
        given(authService.login(any(), any())).willReturn(response);

        mockMvc.perform(post("/v1/auth/naver")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authRequest))
                )
                .andExpect(status().isOk())
                .andDo(document("login-with-naver",
                        requestFields(
                                fieldWithPath("code").description("네이버로 받은 authorization 코드값"),
                                fieldWithPath("redirectUri").description("네이버에 요청했던 redirect uri")
                        ),
                        responseFields(
                                fieldWithPath("grantType").description("인증 토큰 타입 (Bearer)"),
                                fieldWithPath("accessToken").description("엑세스 토큰"),
                                fieldWithPath("accessTokenExpiredDate").description("엑세스 토큰 만료 날짜 (밀리초 단위 타임스탬프)"),
                                fieldWithPath("refreshTokenExpiredDate").description("리프레시 토큰 만료 날짜 (밀리초 단위 타임스탬프)"),
                                fieldWithPath("memberId").description("로그인 한 사용자의 id")
                        ),
                        responseHeaders(
                                headerWithName("Set-Cookie").description("리프레시 토큰을 담은 쿠키")
                        )
                ));
    }
}
