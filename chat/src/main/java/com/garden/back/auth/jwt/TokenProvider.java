package com.graden.back.auth.jwt;

import com.garden.back.member.Member;
import com.graden.back.auth.jwt.repository.CollectionRefreshTokenRepository;
import com.graden.back.auth.jwt.repository.RefreshToken;
import com.graden.back.auth.jwt.repository.RefreshTokenRepository;
import com.graden.back.auth.jwt.response.TokenResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class TokenProvider {

    private final JwtProperties jwtProperties;
    private final RefreshTokenRepository refreshTokenRepository;
    private final Key key;

    public TokenProvider(
            JwtProperties jwtProperties,
            CollectionRefreshTokenRepository refreshTokenRepository
    ) {
        this.jwtProperties = jwtProperties;
        this.refreshTokenRepository = refreshTokenRepository;
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.getTokenSecret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenResponse generateTokenDto(Member member) {
        long now = (new Date().getTime());

        Date accessTokenExpiredDate = new Date(now + jwtProperties.getAccessTokenExpireTime());
        String accessToken = Jwts.builder()
                .setSubject(String.valueOf(member.getId()))
                .claim(jwtProperties.getAuthorityKey(), member.getRole().toString())
                .setExpiration(accessTokenExpiredDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        Date refreshTokenExpiredDate = new Date(now + jwtProperties.getRefreshTokenExpireTime());
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiredDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        RefreshToken refreshTokenObject = new RefreshToken(refreshToken, member, refreshTokenExpiredDate);
        refreshTokenRepository.save(refreshTokenObject);

        return new TokenResponse(
                jwtProperties.getBearerPrefix(),
                accessToken,
                refreshToken,
                accessTokenExpiredDate.getTime(),
                refreshTokenExpiredDate.getTime());
    }

    @SneakyThrows
    public String generateAccessToken(String refreshTokenKey) {
        long now = (new Date().getTime());
        RefreshToken refreshToken = (RefreshToken) refreshTokenRepository.findByKey(refreshTokenKey)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 리프레시 토큰 입니다."));
        Member member = refreshToken.member();

        return Jwts.builder()
                .setSubject(String.valueOf(member.getId()))
                .claim(jwtProperties.getAuthorityKey(), member.getRole().toString())
                .setExpiration(new Date(now + jwtProperties.getAccessTokenExpireTime()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

}
