package com.garden.back.auth.client.naver.response;

public record NaverTokenResponse(
        String access_token,
        String token_type,
        String refresh_token,
        String expires_in,
        String error,
        String error_description
) {
}
