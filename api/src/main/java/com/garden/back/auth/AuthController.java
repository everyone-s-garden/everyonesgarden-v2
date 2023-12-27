package com.garden.back.auth;

import com.garden.back.auth.jwt.response.RefreshTokenResponse;
import com.garden.back.auth.jwt.response.TokenResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/kakao", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> loginWithKakao(@RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok(authService.login(AuthProvider.KAKAO, authorization));
    }

    @PostMapping(value = "/naver", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> loginWithNaver(@RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok(authService.login(AuthProvider.NAVER, authorization));
    }

    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RefreshTokenResponse> getAccessTokenByRefreshToken(@RequestHeader("Refresh") String refreshToken) {
        return ResponseEntity.ok(authService.generateAccessToken(refreshToken));
    }
}
