package com.garden.back.auth;

import com.garden.back.auth.client.AuthRequest;
import com.garden.back.auth.jwt.response.RefreshTokenResponse;
import com.garden.back.auth.jwt.response.TokenResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/kakao", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> loginWithKakao(@RequestBody AuthRequest request) {

        TokenResponse tokenResponse = authService.login(AuthProvider.KAKAO, request);

        ResponseCookie cookie = ResponseCookie.from("authToken", tokenResponse.refreshToken())
            .httpOnly(true)
            .maxAge(tokenResponse.refreshTokenExpiredDate())
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(tokenResponse);
    }

    @PostMapping(value = "/naver", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> loginWithNaver(@RequestBody AuthRequest request) {
        TokenResponse tokenResponse =  authService.login(AuthProvider.NAVER, request);

        ResponseCookie cookie = ResponseCookie.from("authToken", tokenResponse.refreshToken())
            .httpOnly(true)
            .path("/")
            .maxAge(tokenResponse.refreshTokenExpiredDate())
            .build();

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(tokenResponse);
    }

    @PostMapping(value = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RefreshTokenResponse> getAccessTokenByRefreshToken(@RequestHeader("Refresh") String refreshToken) {

        return ResponseEntity.ok(authService.generateAccessToken(refreshToken));
    }
}
