package com.garden.back.weather.infra.response;

public record AllRegionsWeatherInfo(
    String regionName,
    String skyValue,
    String temperatureValue
) {
}