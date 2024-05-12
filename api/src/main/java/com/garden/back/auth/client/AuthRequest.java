package com.garden.back.auth.client;

public record AuthRequest(
        String code,
        String redirectUri //네이버 로그인은 이 필드 필요 없음
) {
}
