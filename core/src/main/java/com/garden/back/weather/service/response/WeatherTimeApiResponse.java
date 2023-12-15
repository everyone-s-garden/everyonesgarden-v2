package com.garden.back.weather.service.response;

import com.garden.back.weather.infra.response.WeatherPerHourAndTomorrowInfo;

import java.util.List;

public record WeatherTimeApiResponse(
   List<WeatherData> weatherTimeResponses,
   String regionName
) {
    public record WeatherData(
        String baseDate,
        String temperature,
        String skyStatus,
        String fsctDate,
        String fsctTime
    ) {
        public static WeatherData from(WeatherPerHourAndTomorrowInfo.WeatherData weatherData) {
            return new WeatherData(weatherData.baseDate(), weatherData.temperature(), weatherData.skyStatus(), weatherData.fsctDate(), weatherData.fsctTime());
        }
    }

    public static WeatherTimeApiResponse from(WeatherPerHourAndTomorrowInfo weatherPerHourAndTomorrowInfo) {
        List<WeatherData> weatherData = weatherPerHourAndTomorrowInfo.weatherData().stream().map(WeatherData::from)
            .toList();
        return new WeatherTimeApiResponse(weatherData, weatherPerHourAndTomorrowInfo.regionName());
    }
}