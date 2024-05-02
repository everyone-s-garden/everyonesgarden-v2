package com.garden.back.auth.client.kakao.response;

public record KakaoTokenResponse(
        String token_type,
        String access_token,
        String expires_in,
        String refresh_token,
        String refresh_token_expires_in,
        String scope
) {

}
