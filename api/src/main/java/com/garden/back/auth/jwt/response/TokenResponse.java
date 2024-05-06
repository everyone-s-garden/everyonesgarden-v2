package com.garden.back.auth.jwt.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record TokenResponse(
    String grantType,
    String accessToken,
    @JsonIgnore
    String refreshToken,
    Long accessTokenExpiredDate,
    Long refreshTokenExpiredDate,
    Long memberId
) {
}
