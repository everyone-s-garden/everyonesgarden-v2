package com.garden.back.region.infra.api;

import com.garden.back.region.infra.config.NaverFeignConfig;
import com.garden.back.weather.infra.api.naver.NaverGeoClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name ="naver-geocode-client",
    url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode",
    fallbackFactory = NaverGeoClient.NaverGeoClientFallback.class,
    configuration = NaverFeignConfig.class
)
public interface NaverGeoCodeClient {

    @GetMapping
    GeoResponse getLatitudeAndLongitude(
        @RequestParam("query") String fullAddress);

}
