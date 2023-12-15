package com.garden.back.weather.infra.response;

import com.garden.back.weather.infra.api.open.response.WeekWeatherResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record WeatherPerHourAndTomorrowInfo(
    List<WeatherData> weatherData,
    String regionName
) {
    public record WeatherData(
        String baseDate,
        String temperature,
        String skyStatus,
        String fsctDate,
        String fsctTime
    ) {}

    public static WeatherPerHourAndTomorrowInfo from(List<WeekWeatherResponse.Response.Body.Items.WeatherItem> items, String regionName) {
        Map<String, WeatherData> groupedData = new HashMap<>();
        Map<String, String> skyConditionMap = Map.of(
            "0", "맑음",
            "1", "비",
            "2", "비/눈",
            "3", "눈",
            "5", "빗방울",
            "6", "빗방울눈날림",
            "7", "눈날림"
        );

        for (var item : items) {
            String key = item.fcstDate() + item.fcstTime();
            groupedData.putIfAbsent(key, new WeatherData(item.fcstDate(), "", "", item.fcstDate(), item.fcstTime()));

            WeatherData currentData = groupedData.get(key);
            if ("TMP".equals(item.category())) {
                groupedData.put(key, new WeatherData(currentData.baseDate(), item.fcstValue(), currentData.skyStatus(), currentData.fsctDate(), currentData.fsctTime()));
            } else if ("PTY".equals(item.category())) {
                groupedData.put(key, new WeatherData(currentData.baseDate(), currentData.temperature(), skyConditionMap.get(item.fcstValue()), currentData.fsctDate(), currentData.fsctTime()));
            }
        }

        List<WeatherData> weatherDataList = new ArrayList<>(groupedData.values());

        return new WeatherPerHourAndTomorrowInfo(weatherDataList, regionName);
    }
}