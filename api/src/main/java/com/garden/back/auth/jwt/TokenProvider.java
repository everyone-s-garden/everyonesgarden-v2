package com.garden.back.auth.jwt;

import com.garden.back.auth.jwt.repository.CollectionRefreshTokenRepository;
import com.garden.back.auth.jwt.repository.RefreshToken;
import com.garden.back.auth.jwt.repository.RefreshTokenRepository;
import com.garden.back.auth.jwt.response.TokenResponse;
import com.garden.back.member.Member;
import com.garden.back.member.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.security.Key;
import java.util.Collections;
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
                refreshTokenExpiredDate.getTime(),
                member.getId()
        );
    }

    public String generateAccessToken(String refreshTokenKey) {
        long now = (new Date().getTime());
        Date accessTokenExpiredDate = new Date(now + jwtProperties.getAccessTokenExpireTime());

        RefreshToken refreshToken = refreshTokenRepository.findByKey(refreshTokenKey)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "유효하지 않은 리프레시 토큰 입니다."));
        Member member = refreshToken.member();

        String accessToken = Jwts.builder()
            .setSubject(String.valueOf(member.getId()))
            .claim(jwtProperties.getAuthorityKey(), member.getRole().toString())
            .setExpiration(accessTokenExpiredDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();

        return Jwts.builder()
                .setSubject(String.valueOf(member.getId()))
                .claim(jwtProperties.getAuthorityKey(), member.getRole().toString())
                .setExpiration(new Date(now + jwtProperties.getAccessTokenExpireTime()))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String accessToken) {

        Claims claims = decodeAccessToken(accessToken);
        Long userId = Long.parseLong(claims.getSubject());

        String authorityKey = Role.USER.getKey() + claims.get(jwtProperties.getAuthorityKey(), String.class);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(authorityKey);

        return new UsernamePasswordAuthenticationToken(userId, null, Collections.singleton(authority));
    }

    public Claims decodeAccessToken(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException expiredJwtException) {
            throw new IllegalStateException("만료된 토큰입니다.");
        }
    }

}
