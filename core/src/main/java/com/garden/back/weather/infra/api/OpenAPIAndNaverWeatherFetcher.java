package com.garden.back.weather.infra.api;

import com.garden.back.global.cache.FallbackResponse;
import com.garden.back.weather.infra.WeatherFetcher;
import com.garden.back.weather.infra.api.naver.NaverGeoClient;
import com.garden.back.weather.infra.api.open.OpenAPIClient;
import com.garden.back.weather.infra.api.open.Region;
import com.garden.back.weather.infra.api.open.RegionProvider;
import com.garden.back.weather.infra.api.open.response.MidWeatherResponse;
import com.garden.back.weather.infra.api.open.response.WeatherForecastResponse;
import com.garden.back.weather.infra.api.open.response.WeekWeatherResponse;
import com.garden.back.weather.infra.response.AllRegionsWeatherInfo;
import com.garden.back.weather.infra.response.WeatherPerHourAndTomorrowInfo;
import com.garden.back.weather.infra.response.WeekWeatherInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@FallbackResponse
public non-sealed class OpenAPIAndNaverWeatherFetcher extends NaverAndOpenAPISupport implements WeatherFetcher {

    @Value("${api.weather.secret}")
    private String secretKey;

    @Value("${api.reverseGeo.id}")
    private String naverApiId;

    @Value("${api.reverseGeo.secret}")
    private String naverApiSecret;

    @Value("${forecast.weekly.publish.time}")
    private String weeklyForecastPublicationTime;

    private final RegionProvider regionProvider;
    private final OpenAPIClient openAPIClient;
    private final NaverGeoClient naverGeoClient;

    public OpenAPIAndNaverWeatherFetcher(
        RegionProvider regionProvider,
        OpenAPIClient openAPIClient,
        NaverGeoClient naverGeoClient
    ) {
        this.regionProvider = regionProvider;
        this.openAPIClient = openAPIClient;
        this.naverGeoClient = naverGeoClient;
    }

    /*
     현재 시간 기준으로 모든 지역의 하늘 상태, 온도를 제공하는 api
     워밍업 고려 어플리케이션 처음 켰을 때 많이 느림
     */
    @Override
    public List<AllRegionsWeatherInfo> getAllRegionsWeatherInfo() {
        String forecastDateTime = super.getNearestForecastDateTime(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());

        String baseDate = forecastDateTime.substring(0, 8); // "20231214"
        String baseTime = forecastDateTime.substring(8);    // "0200"

        List<Pair<Region, WeatherForecastResponse>> pairs = regionProvider.findAll()
            .parallelStream()
            .map(region -> Pair.of(region, openAPIClient.getWeatherForecast(secretKey, 1, 1000, "JSON", baseDate, baseTime, region.nx(), region.ny())))
            .toList();

        return pairs.stream()
            .map(pair -> super.parseWeatherForecastResponse(pair.getFirst(), pair.getSecond()))
            .toList();
    }

    /**
     * 2일 뒤 날씨부터 6일 뒤 날씨까지 제공하는 api
     */
    @Override
    public WeekWeatherInfo getWeekWeatherInfo(String longitude, String latitude) {
        //주간 예보 발표 시간에 맞춰 baseDate를 오늘로 할지 내일로 할지 결정
        String baseDate = super.getBaseDateForWeeklyForecast();

        String regionName = naverGeoClient.getGeoInfoByLongitudeAndLatitude(longitude + "," + latitude, "JSON", naverApiId, naverApiSecret).results().get(0).region().area1().name();

        Region region = regionProvider.findRegionByName(regionName).orElseThrow();
        MidWeatherResponse midLandForecast = openAPIClient.getMidLandForecast(secretKey, 1, 1000, "JSON", region.regionId(), baseDate + weeklyForecastPublicationTime);
        return WeekWeatherInfo.from(midLandForecast, regionName);
    }

    /*
    * 현재시간으로부터 1시간 단위로 5개의 날씨 정보, 다음 날 낮 12시 날씨 정보를 제공하는 api
    * */
    @Override
    public WeatherPerHourAndTomorrowInfo getWeatherPerHourAndTomorrowInfo(String longitude, String latitude) {
        String regionName = naverGeoClient.getGeoInfoByLongitudeAndLatitude(longitude + "," + latitude, "JSON", naverApiId, naverApiSecret).results().get(0).region().area1().name();

        //현재 시간 이후의 매 정시 날씨를 조회하기 위한 시간 값
        String forecastDateTime = super.getNearestForecastDateTime(ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDateTime());
        // 예: forecastDateTime = "202312140200"

        String baseDate = forecastDateTime.substring(0, 8); // "20231214"
        String baseTime = forecastDateTime.substring(8);    // "0200"

        Region region = regionProvider.findRegionByName(regionName).orElseThrow();
        var weekWeatherForecast = openAPIClient.getWeekWeatherForecast(secretKey, 1, 1000, "JSON", baseDate, baseTime, region.nx(), region.ny());

        // 현재 시간 이후의 예측 데이터 필터링
        List<WeekWeatherResponse.Response.Body.Items.WeatherItem> filteredItems =
            super.filterForecastData(weekWeatherForecast);

        Set<String> topFiveForecastTimes =
            super.extractTopFiveForecastTimes(filteredItems);

        // 다음 날의 12시 예측 데이터 확인 및 추가
        List<WeekWeatherResponse.Response.Body.Items.WeatherItem> selectedItems = new ArrayList<>(
            filteredItems.stream()
                .filter(item -> topFiveForecastTimes.contains(item.fcstDate() + item.fcstTime()))
                .toList());

        selectedItems.addAll(
            super.addTomorrowNoonForecast(filteredItems));

        return WeatherPerHourAndTomorrowInfo.from(selectedItems, regionName);
    }

}
