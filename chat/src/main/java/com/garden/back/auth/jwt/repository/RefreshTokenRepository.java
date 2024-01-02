package com.graden.back.auth.jwt.repository;

import java.util.Optional;

public interface RefreshTokenRepository<T> {
    void save(T refreshToken);

    void deleteExpiredToken();

    Optional<T> findByKey(String key);
}
