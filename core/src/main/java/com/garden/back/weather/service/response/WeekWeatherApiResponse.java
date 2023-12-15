package com.garden.back.weather.service.response;

import com.garden.back.weather.infra.response.WeekWeatherInfo;

public record WeekWeatherApiResponse(
    String skyStatusTwoDaysAfter,
    String skyStatusThreeDaysAfter,
    String skyStatusFourDaysAfter,
    String skyStatusFiveDaysAfter,
    String skyStatusSixDaysAfter,
    String regionName
) {
    public static WeekWeatherApiResponse from(WeekWeatherInfo weekWeatherInfo) {
        String skyStatusTwoDaysAfter = weekWeatherInfo.skyStatusTwoDaysAfter();
        String skyStatusThreeDaysAfter = weekWeatherInfo.skyStatusThreeDaysAfter();
        String skyStatusFourDaysAfter = weekWeatherInfo.skyStatusFourDaysAfter();
        String skyStatusFiveDaysAfter = weekWeatherInfo.skyStatusFiveDaysAfter();
        String skyStatusSixDaysAfter = weekWeatherInfo.skyStatusSixDaysAfter();
        String regionName = weekWeatherInfo.regionName();

        return new WeekWeatherApiResponse(
            skyStatusTwoDaysAfter,
            skyStatusThreeDaysAfter,
            skyStatusFourDaysAfter,
            skyStatusFiveDaysAfter,
            skyStatusSixDaysAfter,
            regionName
        );
    }
}