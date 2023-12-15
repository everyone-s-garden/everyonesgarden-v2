package com.garden.back.weather.service.response;

import com.garden.back.weather.infra.response.AllRegionsWeatherInfo;

import java.util.List;

public record AllWeatherResponse(
    List<WeatherData> weatherApiResult
) {
    public record WeatherData(
        String regionName,
        String skyValue,
        String temperatureValue
    ) {}

    public static AllWeatherResponse from(List<AllRegionsWeatherInfo> allRegionsWeatherInfos) {
        List<WeatherData> weatherDataList = allRegionsWeatherInfos.stream()
            .map(info -> new WeatherData(info.regionName(), info.skyValue(), info.temperatureValue()))
            .toList();

        return new AllWeatherResponse(weatherDataList);
    }
}