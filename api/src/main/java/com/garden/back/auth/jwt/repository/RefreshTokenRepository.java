package com.garden.back.auth.jwt.repository;

import java.util.Optional;

public interface RefreshTokenRepository {
    void save(RefreshToken refreshToken);

    void deleteExpiredToken();

    Optional<RefreshToken> findByKey(String key);
}
