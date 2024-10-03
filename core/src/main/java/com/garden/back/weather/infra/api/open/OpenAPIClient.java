package com.garden.back.weather.infra.api.open;

import com.garden.back.weather.infra.api.open.response.MidWeatherResponse;
import com.garden.back.weather.infra.api.open.response.WeatherForecastResponse;
import com.garden.back.weather.infra.api.open.response.WeekWeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

@FeignClient(
    name = "${api.weather.name}",
    url = "${api.weather.baseUrl}"
//    fallbackFactory = OpenAPIClient.WeatherClientFallback.class
)
public interface OpenAPIClient {

    @GetMapping("/VilageFcstInfoService_2.0/getUltraSrtNcst")
    WeatherForecastResponse getWeatherForecast(
        @RequestParam("serviceKey") String serviceKey,
        @RequestParam("pageNo") int pageNo,
        @RequestParam("numOfRows") int numOfRows,
        @RequestParam("dataType") String dataType,
        @RequestParam("base_date") String baseDate,
        @RequestParam("base_time") String baseTime,
        @RequestParam("nx") int nx,
        @RequestParam("ny") int ny
    );

    @GetMapping("/MidFcstInfoService/getMidLandFcst")
    MidWeatherResponse getMidLandForecast(
        @RequestParam("serviceKey") String serviceKey,
        @RequestParam("pageNo") int pageNo,
        @RequestParam("numOfRows") int numOfRows,
        @RequestParam("dataType") String dataType,
        @RequestParam("regId") String regId,
        @RequestParam("tmFc") String tmFc
    );

    @GetMapping("/VilageFcstInfoService_2.0/getVilageFcst")
    WeekWeatherResponse getWeekWeatherForecast(
        @RequestParam("serviceKey") String serviceKey,
        @RequestParam("pageNo") int pageNo,
        @RequestParam("numOfRows") int numOfRows,
        @RequestParam("dataType") String dataType,
        @RequestParam("base_date") String baseDate,
        @RequestParam("base_time") String baseTime,
        @RequestParam("nx") int nx,
        @RequestParam("ny") int ny
    );

    @Slf4j
    @Component
    class WeatherClientFallback implements FallbackFactory<OpenAPIClient> {

        @Override
        public OpenAPIClient create(Throwable cause) {
            log.warn("공공데이터 날씨 정보 조회 응답 오류: {}", cause.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, cause.getMessage());
        }
    }
}
