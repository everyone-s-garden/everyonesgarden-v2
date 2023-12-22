package com.garden.back.weather.service;

import com.garden.back.weather.infra.WeatherFetcher;
import com.garden.back.weather.infra.response.WeekWeatherInfo;
import com.garden.back.weather.service.response.AllWeatherResponse;
import com.garden.back.weather.service.response.WeatherTimeApiResponse;
import com.garden.back.weather.service.response.WeekWeatherApiResponse;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    private final WeatherFetcher weatherFetcher;

    public WeatherService(WeatherFetcher weatherFetcher) {
        this.weatherFetcher = weatherFetcher;
    }

    public AllWeatherResponse getAllRegionsSkyStatusAndTemperature() {
        return AllWeatherResponse.from(weatherFetcher.getAllRegionsWeatherInfo());
    }

    public WeekWeatherApiResponse getWeekSkyStatus(String longitude, String latitude) {
        WeekWeatherInfo weekWeatherInfo = weatherFetcher.getWeekWeatherInfo(longitude, latitude);
        return WeekWeatherApiResponse.from(weekWeatherInfo);
    }

    public WeatherTimeApiResponse getFiveSkyStatusAndTemperatureAfterCurrentTime(String longitude, String latitude) {
        return WeatherTimeApiResponse.from(weatherFetcher.getWeatherPerHourAndTomorrowInfo(longitude, latitude));
    }

}