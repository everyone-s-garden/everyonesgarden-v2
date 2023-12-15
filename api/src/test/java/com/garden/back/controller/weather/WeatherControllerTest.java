package com.garden.back.controller.weather;

import com.garden.back.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WeatherControllerTest extends ControllerTestSupport {

    @DisplayName("현재 위치의 시간별 예보를 조회할 때 위도 경도가 null 또는 범위 밖의 값이 입력되면 400응답을 한다.")
    @ParameterizedTest
    @MethodSource("invalidWeatherByTimeParameters")
    void getInvalidWeatherByTime(String latitude, String longitude) throws Exception {
        mockMvc.perform(get("/v1/weathers/time")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("현재 위치의 주간 예보를 조회할 때 위도 경도가 null 또는 범위 밖의 값이 입력되면 400응답을 한다.")
    @ParameterizedTest
    @MethodSource("invalidWeekWeatherByDateParameters")
    void getInvalidWeekWeatherByDate(String latitude, String longitude) throws Exception {
        mockMvc.perform(get("/v1/weathers/week")
                        .queryParam("latitude", latitude)
                        .queryParam("longitude", longitude)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private static Stream<String[]> invalidWeatherByTimeParameters() {
        return Stream.of(
                new String[] {null, "-74.0060"},
                new String[] {"40.7128", null},
                new String[] {"400", "-74.0060"},
                new String[] {"40.7128", "-740.060"}
        );
    }

    private static Stream<String[]> invalidWeekWeatherByDateParameters() {
        return Stream.of(
                new String[] {null, "-74.0060"},
                new String[] {"40.7128", null},
                new String[] {"400", "-74.0060"},
                new String[] {"40.7128", "-740.060"}
        );
    }
}
