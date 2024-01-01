package com.graden.back.auth.jwt.response;

public record TokenResponse(
    String grantType,
    String accessToken,
    String refreshToken,
    Long accessTokenExpiredDate,
    Long refreshTokenExpiredDate
) {
}
