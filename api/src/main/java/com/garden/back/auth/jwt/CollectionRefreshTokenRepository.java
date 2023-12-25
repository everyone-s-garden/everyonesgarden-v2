package com.garden.back.auth.jwt;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class CollectionRefreshTokenRepository implements RefreshTokenRepository<RefreshToken> {

    private final List<RefreshToken> refreshTokenList = new ArrayList<>();

    public void save(RefreshToken refreshToken) {
        refreshTokenList.add(refreshToken);
    }

    @Override
    public void deleteExpiredToken() {
        Date now = new Date();
        refreshTokenList.removeIf(refreshToken -> refreshToken.expiredDate().before(now));
    }

    @Override
    public Optional<RefreshToken> findByKey(String key) {
        return refreshTokenList.stream()
            .filter(refreshToken -> refreshToken.key().equals(key))
            .findFirst();
    }

}
