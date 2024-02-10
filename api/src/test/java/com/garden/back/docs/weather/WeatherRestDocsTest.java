package com.garden.back.docs.weather;

import com.garden.back.docs.RestDocsSupport;
import com.garden.back.weather.WeatherController;
import com.garden.back.weather.service.WeatherService;
import com.garden.back.weather.service.response.AllWeatherResponse;
import com.garden.back.weather.service.response.WeatherTimeApiResponse;
import com.garden.back.weather.service.response.WeekWeatherApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class WeatherRestDocsTest extends RestDocsSupport {

    WeatherService weatherService = mock(WeatherService.class);

    @Override
    protected Object initController() {
        return new WeatherController(weatherService);
    }

    @DisplayName("모든 지역의 날씨를 조회하는 API DOCS")
    @Test
    void getAllWeather() throws Exception {
        AllWeatherResponse allWeatherResponse = sut.giveMeBuilder(AllWeatherResponse.class)
                .size("weatherApiResult", 17)
                .set("weatherApiResult[0].regionName", "강원도")
                .set("weatherApiResult[0].skyValue", "맑음")
                .set("weatherApiResult[0].temperatureValue", "5.5")
                .set("weatherApiResult[1].regionName", "서울특별시")
                .set("weatherApiResult[1].skyValue", "맑음")
                .set("weatherApiResult[1].temperatureValue", "8.1")
                .set("weatherApiResult[2].regionName", "경기도")
                .set("weatherApiResult[2].skyValue", "맑음")
                .set("weatherApiResult[2].temperatureValue", "9.6")
                .set("weatherApiResult[3].regionName", "경상남도")
                .set("weatherApiResult[3].skyValue", "맑음")
                .set("weatherApiResult[3].temperatureValue", "9.6")
                .set("weatherApiResult[4].regionName", "경상북도")
                .set("weatherApiResult[4].skyValue", "맑음")
                .set("weatherApiResult[4].temperatureValue", "7.5")
                .set("weatherApiResult[5].regionName", "광주광역시")
                .set("weatherApiResult[5].skyValue", "맑음")
                .set("weatherApiResult[5].temperatureValue", "11.4")
                .set("weatherApiResult[6].regionName", "대구광역시")
                .set("weatherApiResult[6].skyValue", "맑음")
                .set("weatherApiResult[6].temperatureValue", "7.5")
                .set("weatherApiResult[7].regionName", "대전광역시")
                .set("weatherApiResult[7].skyValue", "맑음")
                .set("weatherApiResult[7].temperatureValue", "9.2")
                .set("weatherApiResult[8].regionName", "부산광역시")
                .set("weatherApiResult[8].skyValue", "맑음")
                .set("weatherApiResult[8].temperatureValue", "10.7")
                .set("weatherApiResult[9].regionName", "세종특별자치시")
                .set("weatherApiResult[9].skyValue", "맑음")
                .set("weatherApiResult[9].temperatureValue", "9.5")
                .set("weatherApiResult[10].regionName", "울산광역시")
                .set("weatherApiResult[10].skyValue", "맑음")
                .set("weatherApiResult[10].temperatureValue", "7.6")
                .set("weatherApiResult[11].regionName", "인천광역시")
                .set("weatherApiResult[11].skyValue", "맑음")
                .set("weatherApiResult[11].temperatureValue", "9")
                .set("weatherApiResult[12].regionName", "전라남도")
                .set("weatherApiResult[12].skyValue", "비")
                .set("weatherApiResult[12].temperatureValue", "11.2")
                .set("weatherApiResult[13].regionName", "전라북도")
                .set("weatherApiResult[13].skyValue", "맑음")
                .set("weatherApiResult[13].temperatureValue", "12")
                .set("weatherApiResult[14].regionName", "제주특별자치도")
                .set("weatherApiResult[14].skyValue", "비")
                .set("weatherApiResult[14].temperatureValue", "16.3")
                .set("weatherApiResult[15].regionName", "충청남도")
                .set("weatherApiResult[15].skyValue", "맑음")
                .set("weatherApiResult[15].temperatureValue", "8.9")
                .set("weatherApiResult[16].regionName", "충청북도")
                .set("weatherApiResult[16].skyValue", "맑음")
                .set("weatherApiResult[16].temperatureValue", "10.7")
                .sample();

        given(weatherService.getAllRegionsSkyStatusAndTemperature()).willReturn(allWeatherResponse);

        mockMvc.perform(get("/v1/weathers/all")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-all-weather",
                        responseFields(
                                fieldWithPath("weatherApiResult").description("날씨 API 결과 목록"),
                                fieldWithPath("weatherApiResult[].regionName").type(JsonFieldType.STRING).description("지역 이름"),
                                fieldWithPath("weatherApiResult[].skyValue").type(JsonFieldType.STRING).description("하늘 상태"),
                                fieldWithPath("weatherApiResult[].temperatureValue").type(JsonFieldType.STRING).description("온도 값")
                        )
                ));

    }

    @DisplayName("현재 지역의 시간별 날씨와 내일의 날씨를 조회하는 API DOCS")
    @Test
    void getWeatherByTime() throws Exception {
        WeatherTimeApiResponse weatherTimeApiResponse = sut.giveMeBuilder(WeatherTimeApiResponse.class)
                .size("weatherTimeResponses", 6)
                .set("weatherTimeResponses[0].baseDate", "20231214")
                .set("weatherTimeResponses[0].temperature", "9")
                .set("weatherTimeResponses[0].skyStatus", "비")
                .set("weatherTimeResponses[0].fsctDate", "20231214")
                .set("weatherTimeResponses[0].fsctTime", "1000")

                .set("weatherTimeResponses[1].baseDate", "20231214")
                .set("weatherTimeResponses[1].temperature", "9")
                .set("weatherTimeResponses[1].skyStatus", "비")
                .set("weatherTimeResponses[1].fsctDate", "20231214")
                .set("weatherTimeResponses[1].fsctTime", "1100")

                .set("weatherTimeResponses[2].baseDate", "20231214")
                .set("weatherTimeResponses[2].temperature", "9")
                .set("weatherTimeResponses[2].skyStatus", "비")
                .set("weatherTimeResponses[2].fsctDate", "20231214")
                .set("weatherTimeResponses[2].fsctTime", "1200")

                .set("weatherTimeResponses[3].baseDate", "20231214")
                .set("weatherTimeResponses[3].temperature", "10")
                .set("weatherTimeResponses[3].skyStatus", "비")
                .set("weatherTimeResponses[3].fsctDate", "20231214")
                .set("weatherTimeResponses[3].fsctTime", "1300")

                .set("weatherTimeResponses[4].baseDate", "20231214")
                .set("weatherTimeResponses[4].temperature", "9")
                .set("weatherTimeResponses[4].skyStatus", "비")
                .set("weatherTimeResponses[4].fsctDate", "20231214")
                .set("weatherTimeResponses[4].fsctTime", "1400")

                .set("weatherTimeResponses[5].baseDate", "20231215")
                .set("weatherTimeResponses[5].temperature", "7")
                .set("weatherTimeResponses[5].skyStatus", "비")
                .set("weatherTimeResponses[5].fsctDate", "20231215")
                .set("weatherTimeResponses[5].fsctTime", "1200")

                .set("regionName", "경기도")
                .sample();

        given(weatherService.getFiveSkyStatusAndTemperatureAfterCurrentTime(any(), any())).willReturn(weatherTimeApiResponse);

        mockMvc.perform(get("/v1/weathers/time")
                        .queryParam("latitude", "37.289984")
                        .queryParam("longitude","127.0284288")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andExpect(status().isOk())
                .andDo(print())
                .andDo(document("get-tomorrow-weather-and-five-weather-after-current-time",
                        queryParameters(
                                parameterWithName("latitude").description("위도"),
                                parameterWithName("longitude").description("경도")
                        ),
                        responseFields(
                                fieldWithPath("weatherTimeResponses").description("날씨 시간별 응답 목록"),
                                fieldWithPath("weatherTimeResponses[].baseDate").type(JsonFieldType.STRING).description("기준 날짜"),
                                fieldWithPath("weatherTimeResponses[].temperature").type(JsonFieldType.STRING).description("온도"),
                                fieldWithPath("weatherTimeResponses[].skyStatus").type(JsonFieldType.STRING).description("하늘 상태"),
                                fieldWithPath("weatherTimeResponses[].fsctDate").type(JsonFieldType.STRING).description("예측 날짜"),
                                fieldWithPath("weatherTimeResponses[].fsctTime").type(JsonFieldType.STRING).description("예측 시간(1시간 단위, 마지막은 다음 날의 날씨 정보)"),
                                fieldWithPath("regionName").type(JsonFieldType.STRING).description("지역 이름"),

                                fieldWithPath("weatherTimeResponses[5].baseDate").description("다음 날의 예측의 기준 날짜"),
                                fieldWithPath("weatherTimeResponses[5].temperature").description("다음 날의 예측의 온도"),
                                fieldWithPath("weatherTimeResponses[5].skyStatus").description("다음 날의 예측의 하늘 상태"),
                                fieldWithPath("weatherTimeResponses[5].fsctDate").description("다음 날의 예측의 예측 날짜"),
                                fieldWithPath("weatherTimeResponses[5].fsctTime").description("다음 날의 예측의 예측 시간")
                        )
                ));
    }

    @DisplayName("현재 지역의 2일 뒤부터 9일뒤 까지의 날씨를 조회하는 API DOCS")
    @Test
    void getWeekWeatherByDate() throws Exception {
        WeekWeatherApiResponse weekWeatherApiResponse = new WeekWeatherApiResponse(
            Arrays.asList("맑음", "흐림", "흐리고 비", "맑음", "맑음"), // 5개의 날씨 상태만 포함
            "서울"
        );

        given(weatherService.getWeekSkyStatus(any(), any())).willReturn(weekWeatherApiResponse);

        mockMvc.perform(get("/v1/weathers/week")
                .queryParam("latitude", "37.289984")
                .queryParam("longitude", "127.0284288")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andExpect(status().isOk())
            .andDo(print())
            .andDo(document("get-week-weather",
                queryParameters(
                    parameterWithName("latitude").description("위도"),
                    parameterWithName("longitude").description("경도")
                ),
                responseFields(
                    fieldWithPath("status").description("날씨 상태 정보 리스트(n번째 값이 n+2일 뒤의 날씨임 예를 들어 0번째 원소는 2일 뒤 날씨)").type(JsonFieldType.ARRAY),
                    fieldWithPath("regionName").description("지역 이름").type(JsonFieldType.STRING)
                )
            ));
    }


}
