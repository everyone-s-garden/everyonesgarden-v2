package com.garden.back.weather.infra.api.open;

import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.weather.infra.api.NaverAndOpenAPISupport;
import com.garden.back.weather.infra.api.OpenAPIAndNaverWeatherFetcher;
import com.garden.back.weather.infra.api.naver.response.GeoResponse;
import com.garden.back.weather.infra.api.open.response.MidWeatherResponse;
import com.garden.back.weather.infra.api.open.response.WeatherForecastResponse;
import com.garden.back.weather.infra.api.open.response.WeekWeatherResponse;
import com.garden.back.weather.infra.response.AllRegionsWeatherInfo;
import com.garden.back.weather.infra.response.WeatherPerHourAndTomorrowInfo;
import com.garden.back.weather.infra.response.WeekWeatherInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

class OpenAPIAndNaverWeatherFetcherTest extends IntegrationTestSupport {

    @Autowired
    RegionProvider regionProvider;

    @Autowired
    NaverAndOpenAPISupport naverAndOpenAPISupport;

    @Autowired
    OpenAPIAndNaverWeatherFetcher weatherFetcher;

    @DisplayName("현재 시간 기준으로 모든 지역의 하늘 상태, 온도를 조회할 수 있다.")
    @Test
    void getAllRegionsWeatherInfo() {
        // given
        WeatherForecastResponse weatherForecastResponse = sut.giveMeBuilder(WeatherForecastResponse.class)
            .size("response.body.items.item", 2)
            .set("response.body.items.item[0].category", "PTY")
            .set("response.body.items.item[0].obsrValue", "0") // 맑음
            .set("response.body.items.item[0].baseTime", "0600")
            .set("response.body.items.item[0].nx", 60)
            .set("response.body.items.item[0].ny", 127)
            .set("response.body.items.item[1].category", "T1H")
            .set("response.body.items.item[1].obsrValue", "20") // 20도
            .set("response.body.items.item[1].baseTime", "0600")
            .set("response.body.items.item[1].nx", 60)
            .set("response.body.items.item[1].ny", 127)
            .sample();

        given(openAPIClient.getWeatherForecast(anyString(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyInt(), anyInt())).willReturn(weatherForecastResponse);

        //when
        List<AllRegionsWeatherInfo> result = weatherFetcher.getAllRegionsWeatherInfo();

        // then
        AllRegionsWeatherInfo expectedWeatherInfo = sut.giveMeBuilder(AllRegionsWeatherInfo.class)
            .set("regionName", "광주광역시")
            .set("skyValue", "맑음")
            .set("temperatureValue", "20")
            .sample();
        assertThat(result).contains(expectedWeatherInfo)
            .hasSize(17);
    }

    @DisplayName("2일 뒤 날씨부터 9일 뒤 날씨까지 조회할 수 있다.")
    @Test
    void getWeekWeatherInfo() {
        //given
        GeoResponse geoResponse = sut.giveMeBuilder(GeoResponse.class)
            .size("results", 1)
            .set("results[0].region.area1.name", "경기도")
            .sample();
        given(naverGeoClient.getGeoInfoByLongitudeAndLatitude(anyString(), anyString(), anyString(), anyString())).willReturn(geoResponse);
        MidWeatherResponse midWeatherResponse = sut.giveMeBuilder(MidWeatherResponse.class)
            .size("response.body.items.item", 1)
            .set("response.body.items.item[0].wf3Am", "맑음")
            .set("response.body.items.item[0].wf4Am", "맑음") // 맑음
            .set("response.body.items.item[0].wf5Am", "맑음")
            .set("response.body.items.item[0].wf6Am", "맑음")
            .set("response.body.items.item[0].wf7Am", "맑음")
            .set("response.body.items.item[0].wf8", "맑음")
            .set("response.body.items.item[0].wf9", "맑음")
            .set("response.body.items.item[0].wf10", "맑음")

            .sample();
        given(openAPIClient.getMidLandForecast(anyString(), anyInt(), anyInt(), anyString(), anyString(), anyString())).willReturn(midWeatherResponse);

        //when
        WeekWeatherInfo actual = weatherFetcher.getWeekWeatherInfo("123", "123");

        System.out.println(actual);

        //then
        WeekWeatherInfo expected = sut.giveMeBuilder(WeekWeatherInfo.class)
            .set("skyStatusTwoDaysAfter", "맑음")
            .set("skyStatusThreeDaysAfter", "맑음")
            .set("skyStatusFourDaysAfter", "맑음")
            .set("skyStatusFiveDaysAfter", "맑음")
            .set("skyStatusSixDaysAfter", "맑음")
            .set("skyStatusSevenDaysAfter", "맑음")
            .set("skyStatusEightDaysAfter", "맑음")
            .set("skyStatusNineDaysAfter", "맑음")
            .set("regionName", "경기도")
            .sample();
        assertThat(actual).isEqualTo(expected);

    }

    @DisplayName("현재시간으로부터 1시간 단위로 5개의 날씨 정보, 다음 날 낮 12시 날씨 정보를 조회할 수 있다.")
    @Test
    void getWeatherPerHourAndTomorrowInfo() {
        //given
        GeoResponse geoResponse = sut.giveMeBuilder(GeoResponse.class)
            .size("results", 1)
            .set("results[0].region.area1.name", "경기도")
            .sample();
        given(naverGeoClient.getGeoInfoByLongitudeAndLatitude(anyString(), anyString(), anyString(), anyString())).willReturn(geoResponse);

        String todayDate = LocalDate.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.BASIC_ISO_DATE);
        String futureTimeFormatted = LocalTime.now(ZoneId.of("Asia/Seoul")).plusHours(1).format(DateTimeFormatter.ofPattern("HHmm"));

        WeekWeatherResponse weatherResponse = sut
            .giveMeBuilder(WeekWeatherResponse.class)
            .size("response.body.items.item", 2)
            .set("response.body.items.item[0].category", "PTY")
            .set("response.body.items.item[0].fcstDate", todayDate)
            .set("response.body.items.item[0].fcstTime", futureTimeFormatted)
            .set("response.body.items.item[0].fcstValue", "0") // 맑음
            .set("response.body.items.item[1].category", "TMP")
            .set("response.body.items.item[1].fcstDate", todayDate)
            .set("response.body.items.item[1].fcstTime", futureTimeFormatted)
            .set("response.body.items.item[1].fcstValue", "20")
            .sample();

        given(openAPIClient.getWeekWeatherForecast(anyString(), anyInt(), anyInt(), anyString(), anyString(), anyString(), anyInt(), anyInt())).willReturn(weatherResponse);

        //when
        WeatherPerHourAndTomorrowInfo actual = weatherFetcher.getWeatherPerHourAndTomorrowInfo("123", "123");

        //then
        WeatherPerHourAndTomorrowInfo expected = sut.giveMeBuilder(WeatherPerHourAndTomorrowInfo.class)
            .size("weatherData", 1)
            .set("weatherData[0].baseDate", todayDate)
            .set("weatherData[0].temperature", "20")
            .set("weatherData[0].skyStatus", "맑음")
            .set("weatherData[0].fsctDate", todayDate)
            .set("weatherData[0].fsctTime", futureTimeFormatted) // fsctTime을 "1358"로 설정
            .set("regionName", "경기도")
            .sample();
        assertThat(expected).isEqualTo(actual);
    }
}