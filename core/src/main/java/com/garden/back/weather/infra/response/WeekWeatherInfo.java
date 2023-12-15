package com.garden.back.weather.infra.response;

import com.garden.back.weather.infra.api.open.response.MidWeatherResponse;

public record WeekWeatherInfo(
    String skyStatusTwoDaysAfter,
    String skyStatusThreeDaysAfter,
    String skyStatusFourDaysAfter,
    String skyStatusFiveDaysAfter,
    String skyStatusSixDaysAfter,
    String regionName
) {

    public static WeekWeatherInfo from(MidWeatherResponse midWeatherResponse, String regionName) {
        var items = midWeatherResponse.response().body().items().item();
        if (items.isEmpty()) {
            throw new IllegalStateException("주간 날씨로 부터 조회된 데이터 없음");
        }

        var weatherItem = items.get(0);

        String wf3Am = weatherItem.wf3Am();
        String wf4Am = weatherItem.wf4Am();
        String wf5Am = weatherItem.wf5Am();
        String wf6Am = weatherItem.wf6Am();
        String wf7Am = weatherItem.wf7Am();

        return new WeekWeatherInfo(wf3Am, wf4Am, wf5Am, wf6Am, wf7Am, regionName);
    }
}