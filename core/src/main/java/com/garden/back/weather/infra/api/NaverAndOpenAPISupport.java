package com.garden.back.weather.infra.api;

import com.garden.back.weather.infra.api.open.Region;
import com.garden.back.weather.infra.api.open.response.WeatherForecastResponse;
import com.garden.back.weather.infra.api.open.response.WeekWeatherResponse;
import com.garden.back.weather.infra.response.AllRegionsWeatherInfo;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * getAllRegionsWeatherInfo의 흐름을 하나씩 담은 확장 불가능한 클래스 (가독성을 위해서 만든 클래스)
 * */
public sealed class NaverAndOpenAPISupport permits OpenAPIAndNaverWeatherFetcher {

    private static final String SEOUL = "Asia/Seoul";

    public AllRegionsWeatherInfo parseWeatherForecastResponse(Region region, WeatherForecastResponse response) {
        Map<String, String> skyConditionMap = getSkyConditionMap();
        String skyValue = "";
        String temperatureValue = "";
        for (var item : response.response().body().items().item()) {
            if (item.category().equals("PTY")) {
                String ptyValue = item.obsrValue();
                skyValue = skyConditionMap.getOrDefault(ptyValue, "알 수 없음");
            } else if (item.category().equals("T1H")) {
                temperatureValue = item.obsrValue();
            }
        }
        return new AllRegionsWeatherInfo(region.regionName(), skyValue, temperatureValue);
    }

    private Map<String, String> getSkyConditionMap() {
        return Map.of(
            "0", "맑음",
            "1", "비",
            "2", "비/눈",
            "3", "눈",
            "5", "빗방울",
            "6", "빗방울눈날림",
            "7", "눈날림"
        );
    }

    public String getBaseDateForWeeklyForecast() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of(SEOUL));

        if (now.getHour() >= 6) {
            return now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        }
        return now.minusDays(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public String getNearestForecastDateTime(LocalDateTime currentTime) {
        int[] forecastHours = {23, 20, 17, 14, 11, 8, 5, 2};
        currentTime = currentTime.minusHours(3);
        LocalDateTime baseTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), 2, 0);

        for (int hour : forecastHours) {
            baseTime = LocalDateTime.of(currentTime.getYear(), currentTime.getMonth(), currentTime.getDayOfMonth(), hour, 0);
            if (baseTime.isBefore(currentTime)) {
                break;
            }
        }

        if (baseTime.isAfter(currentTime)) {
            baseTime = LocalDateTime.of(currentTime.minusDays(1).getYear(), currentTime.minusDays(1).getMonth(), currentTime.minusDays(1).getDayOfMonth(), 23, 0);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        return baseTime.format(formatter);
    }


    public List<WeekWeatherResponse.Response.Body.Items.WeatherItem> filterForecastData(
        WeekWeatherResponse weekWeatherForecast) {
        ZonedDateTime nowSeoul = ZonedDateTime.now(ZoneId.of(SEOUL));

        return weekWeatherForecast.response().body().items().item().stream()
            .filter(item -> item.category().equals("TMP") || item.category().equals("PTY"))
            .filter(item -> LocalDateTime.of(LocalDate.parse(item.fcstDate(), DateTimeFormatter.BASIC_ISO_DATE),
                    LocalTime.parse(item.fcstTime(), DateTimeFormatter.ofPattern("HHmm")))
                .atZone(ZoneId.of(SEOUL))
                .isAfter(nowSeoul))
            .toList();
    }

    public Set<String> extractTopFiveForecastTimes(
        List<WeekWeatherResponse.Response.Body.Items.WeatherItem> filteredItems) {
        return filteredItems.stream()
            .map(item -> item.fcstDate() + item.fcstTime())
            .distinct()
            .limit(5)
            .collect(Collectors.toSet());
    }

    public List<WeekWeatherResponse.Response.Body.Items.WeatherItem> addTomorrowNoonForecast(
        List<WeekWeatherResponse.Response.Body.Items.WeatherItem> filteredItems) {
        ZonedDateTime tomorrowNoonSeoul = ZonedDateTime.now(ZoneId.of(SEOUL)).plusDays(1).withHour(12).withMinute(0).withSecond(0).withNano(0);
        String tomorrowNoonKey = tomorrowNoonSeoul.format(DateTimeFormatter.BASIC_ISO_DATE) + "1200";

        return filteredItems.stream()
            .filter(item -> (item.fcstDate() + item.fcstTime()).equals(tomorrowNoonKey))
            .toList();
    }

}