package com.garden.back.auth.client.naver;

import com.garden.back.auth.client.naver.response.NaverOauth2Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
    name = "${oauth2.naver.name}",
    url = "${oauth2.naver.url}",
    fallbackFactory = NaverOAuthClient.NaverOAuthClientFallback.class
)
public interface NaverOAuthClient {

    @GetMapping
    NaverOauth2Response getUserInfoFromKakao(@RequestHeader(name = "Authorization") String authorization);

    @Slf4j
    @Component
    class NaverOAuthClientFallback implements FallbackFactory<NaverOAuthClient> {

        @Override
        public NaverOAuthClient create(Throwable cause) {
            log.warn("Naver OAuth2 오류 {}", cause.getMessage());
            throw new IllegalArgumentException(cause.getMessage());
        }
    }
}
