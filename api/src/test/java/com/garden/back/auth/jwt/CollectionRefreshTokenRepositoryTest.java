package com.garden.back.auth.jwt;

import com.garden.back.auth.jwt.repository.CollectionRefreshTokenRepository;
import com.garden.back.auth.jwt.repository.RefreshToken;
import com.garden.back.global.FixtureSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class CollectionRefreshTokenRepositoryTest extends FixtureSupport {

    private CollectionRefreshTokenRepository repository = new CollectionRefreshTokenRepository();

    @DisplayName("토큰을 저장한다.")
    @Test
    void save() {
        //given
        RefreshToken token = sut.giveMeOne(RefreshToken.class);

        //when
        repository.save(token);

        //then
        assertThat(repository.findByKey(token.key())).isEqualTo(Optional.of(token));
    }

    @DisplayName("만료된 토큰을 삭제한다.")
    @Test
    void deleteExpiredToken() {
        //given
        long currentTime = System.currentTimeMillis();
        RefreshToken validToken = sut.giveMeBuilder(RefreshToken.class)
            .set("expiredDate", new Date(currentTime + 10000))
            .sample();
        // 10초 전에 만료된 토큰 생성
        RefreshToken expiredToken = sut.giveMeBuilder(RefreshToken.class)
            .set("expiredDate", new Date(currentTime - 10000))
            .sample();

        //when
        repository.save(validToken);
        repository.save(expiredToken);
        repository.deleteExpiredToken();

        //then
        assertThat(repository.findByKey(expiredToken.key())).isEmpty();
        assertThat(repository.findByKey(validToken.key())).isEqualTo(Optional.of(validToken));
    }

}
