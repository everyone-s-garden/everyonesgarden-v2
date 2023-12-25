package com.garden.back.auth;

import com.garden.back.auth.jwt.response.TokenResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/v1")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/kakao", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TokenResponse> loginWithKakao(@RequestHeader("Authorization") String authorization) {
        return ResponseEntity.ok(authService.login(AuthProvider.KAKAO, authorization));
    }
}
