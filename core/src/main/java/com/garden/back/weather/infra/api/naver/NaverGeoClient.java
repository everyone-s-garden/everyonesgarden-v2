package com.garden.back.weather.infra.api.naver;

import com.garden.back.weather.infra.api.naver.response.GeoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@FeignClient(
    name = "naverGeo",
    url = "${api.reverseGeo.url}",
    fallbackFactory = NaverGeoClient.NaverGeoClientFallback.class
)
public interface NaverGeoClient {


    @GetMapping
    GeoResponse getGeoInfoByLongitudeAndLatitude(
        @RequestParam("coords") String coords,
        @RequestParam("output") String output,
        @RequestHeader("X-NCP-APIGW-API-KEY-ID") String apiId,
        @RequestHeader("X-NCP-APIGW-API-KEY") String apiKey
    );

    @Slf4j
    @Component
    class NaverGeoClientFallback implements FallbackFactory<NaverGeoClient> {

        @Override
        public NaverGeoClient create(Throwable cause) {
            log.warn("공공데이터 날씨 정보 조회 응답 오류: {}", cause.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, cause.getMessage());
        }
    }
}