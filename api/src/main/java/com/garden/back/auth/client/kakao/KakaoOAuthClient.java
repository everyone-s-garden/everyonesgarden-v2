package com.garden.back.auth.client.kakao;

import com.garden.back.auth.client.kakao.response.KakaoOauth2Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    name = "${oauth2.kakao.name}",
    url = "${oauth2.kakao.url}",
    fallbackFactory = KakaoOAuthClient.KakaoOAuthClientFallback.class
)
public interface KakaoOAuthClient {

    @GetMapping
    KakaoOauth2Response getUserInfoFromKakao(@RequestHeader(name = "Authorization") String authorization);

    @Slf4j
    @Component
    class KakaoOAuthClientFallback implements FallbackFactory<KakaoOAuthClient> {

        @Override
        public KakaoOAuthClient create(Throwable cause) {
            log.warn("KaKao OAuth2 오류 {}", cause.getMessage());
            throw new IllegalArgumentException(cause.getMessage());
        }
    }
}
