package com.garden.back.weather.service.response;

import com.garden.back.weather.infra.response.WeekWeatherInfo;

import java.util.ArrayList;
import java.util.List;

public record WeekWeatherApiResponse(
    List<String> status, // 날씨 상태 정보를 담을 문자열 리스트
    String regionName // 지역 이름
) {
    public static WeekWeatherApiResponse from(WeekWeatherInfo weekWeatherInfo) {
        List<String> statuses = new ArrayList<>();
        // 각 일자별 날씨 상태를 리스트에 추가
        statuses.add(weekWeatherInfo.skyStatusTwoDaysAfter());
        statuses.add(weekWeatherInfo.skyStatusThreeDaysAfter());
        statuses.add(weekWeatherInfo.skyStatusFourDaysAfter());
        statuses.add(weekWeatherInfo.skyStatusFiveDaysAfter());
        statuses.add(weekWeatherInfo.skyStatusSixDaysAfter());
        statuses.add(weekWeatherInfo.skyStatusSevenDaysAfter());
        statuses.add(weekWeatherInfo.skyStatusEightDaysAfter());
        statuses.add(weekWeatherInfo.skyStatusNineDaysAfter());

        String regionName = weekWeatherInfo.regionName(); // 지역 이름 설정

        return new WeekWeatherApiResponse(statuses, regionName);
    }
}
