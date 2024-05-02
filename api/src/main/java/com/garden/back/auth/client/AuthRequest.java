package com.garden.back.auth.client;

public record AuthRequest(
        String code,
        String redirectUri
) {
}
