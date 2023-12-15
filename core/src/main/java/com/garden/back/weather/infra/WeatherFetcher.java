package com.garden.back.weather.infra;

import com.garden.back.weather.infra.response.AllRegionsWeatherInfo;
import com.garden.back.weather.infra.response.WeatherPerHourAndTomorrowInfo;
import com.garden.back.weather.infra.response.WeekWeatherInfo;

import java.util.List;

public interface WeatherFetcher {
    List<AllRegionsWeatherInfo> getAllRegionsWeatherInfo();

    WeekWeatherInfo getWeekWeatherInfo(String longitude, String latitude);

    WeatherPerHourAndTomorrowInfo getWeatherPerHourAndTomorrowInfo(String longitude, String latitude);
}