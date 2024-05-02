package com.garden.back.auth.client.kakao;

import com.garden.back.auth.client.kakao.response.KakaoTokenResponse;
import com.garden.back.auth.client.kakao.response.KakaoOauth2Response;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @FeignClient(
            name = "kakaoToken",
            url = "https://kauth.kakao.com/oauth/token",
            fallbackFactory = KakaoOAuthClient.KakaoOAuthClientFallback.class,
            configuration = FormFeignEncoderConfig.class
    )
    interface KakaoTokenClient {
        @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        KakaoTokenResponse getToken(@RequestBody KakaoTokenRequest requestDto);
    }

    class FormFeignEncoderConfig {
        @Bean
        public Encoder encoder(ObjectFactory<HttpMessageConverters> converters) {
            return new SpringFormEncoder(new SpringEncoder(converters));
        }
    }
}
