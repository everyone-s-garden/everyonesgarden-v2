package com.garden.back.weather.service;

import com.garden.back.global.MockTestSupport;
import com.garden.back.weather.infra.WeatherFetcher;
import com.garden.back.weather.infra.response.AllRegionsWeatherInfo;
import com.garden.back.weather.infra.response.WeatherPerHourAndTomorrowInfo;
import com.garden.back.weather.infra.response.WeekWeatherInfo;
import com.garden.back.weather.service.response.AllWeatherResponse;
import com.garden.back.weather.service.response.WeatherTimeApiResponse;
import com.garden.back.weather.service.response.WeekWeatherApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.BDDMockito;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

class WeatherServiceTest extends MockTestSupport {

    @InjectMocks
    private WeatherService weatherService;

    @Mock
    private WeatherFetcher weatherFetcher;

    @DisplayName("모든 지역의 하늘 상태, 온도를 조회할 수 있다.")
    @Test
    void getAllRegionsSkyStatusAndTemperature() {
        // given
        List<AllRegionsWeatherInfo> expectedResponse = sut.giveMeBuilder(AllRegionsWeatherInfo.class)
                .sampleList(5);
        given(weatherFetcher.getAllRegionsWeatherInfo()).willReturn(expectedResponse);


        // when
        AllWeatherResponse actual = weatherService.getAllRegionsSkyStatusAndTemperature();

        // then
        then(actual).isEqualTo(AllWeatherResponse.from(expectedResponse));
    }

    @DisplayName("현재 위치의 일주일치 하늘의 정보를 조회할 수 있다.")
    @Test
    void getWeekSkyStatus() {
        // given
        String longitude = "123";
        String latitude = "456";
        WeekWeatherInfo mockInfo = sut.giveMeOne(WeekWeatherInfo.class);
        given(weatherFetcher.getWeekWeatherInfo(longitude, latitude)).willReturn(mockInfo);

        // when
        WeekWeatherApiResponse response = weatherService.getWeekSkyStatus(longitude, latitude);

        // then
        then(response).isEqualTo(WeekWeatherApiResponse.from(mockInfo));
    }

    @DisplayName("현재 위치의 5시간 어치의 시간과 온도를 조회할 수 있다.")
    @Test
    void getFiveSkyStatusAndTemperatureAfterCurrentTime() {
        // given
        String longitude = "123";
        String latitude = "456";
        WeatherPerHourAndTomorrowInfo expectedResponse = sut.giveMeOne(WeatherPerHourAndTomorrowInfo.class);

        given(weatherFetcher.getWeatherPerHourAndTomorrowInfo(longitude, latitude)).willReturn(expectedResponse);

        // when
        WeatherTimeApiResponse actualResponse = weatherService.getFiveSkyStatusAndTemperatureAfterCurrentTime(longitude, latitude);

        // then
        then(actualResponse).isEqualTo(WeatherTimeApiResponse.from(expectedResponse));
    }
}
