package com.garden.back.docs.region;

import com.garden.back.docs.RestDocsSupport;
import com.garden.back.region.LocationSearchApiRequest;
import com.garden.back.region.LocationSearchApiResponses;
import com.garden.back.region.RegionController;
import com.garden.back.region.RegionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RegionRestDocsTest extends RestDocsSupport {

    RegionService regionService = mock(RegionService.class);

    @Override
    protected Object initController() {
        return new RegionController(regionService);
    }

    @DisplayName("지역 명 검색 api docs")
    @Test
    void findByRegionName() throws Exception {
        LocationSearchApiRequest request = new LocationSearchApiRequest("서울시 성동구 금호동", 0, 5);

        LocationSearchApiResponses responses = sut.giveMeBuilder(LocationSearchApiResponses.class)
            .size("locationSearchResponses", 1)
            .set("locationSearchResponses[0].position", "서울시 성동구 금호동")
            .set("locationSearchResponses[0].latitude", 37.567924)
            .set("locationSearchResponses[0].longitude", 127.07849)
            .sample();

        given(regionService.autoCompleteRegion(any())).willReturn(responses);

        mockMvc.perform(get("/v1/regions")
                .param("address", request.address())
                .param("offset", String.valueOf(request.offset()))
                .param("limit", String.valueOf(request.limit()))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-regions",
                queryParameters(
                    parameterWithName("address").description("조회할 주소"),
                    parameterWithName("offset").description("조회를 시작할 데이터의 위치"),
                    parameterWithName("limit").description("해당 페이지에서 조회할 데이터의 개수")
                ),
                responseFields(
                    fieldWithPath("locationSearchResponses").type(ARRAY).description("작물 게시글 정보 목록"),
                    fieldWithPath("locationSearchResponses[].latitude").type(NUMBER).description("위도"),
                    fieldWithPath("locationSearchResponses[].longitude").type(NUMBER).description("경도"),
                    fieldWithPath("locationSearchResponses[].position").type(STRING).description("주소")
                )
            ));
    }

    @DisplayName("지역명에 대한 위도 경도 반환 API")
    @Test
    void getLatitudeAndLongitude() throws Exception {
        given(regionService.getLatitudeAndLongitude(any())).willReturn(RegionFixture.geoResult());

        mockMvc.perform(get("/v1/regions/geocode")
                .param("fullAddress", "인천광역시 서구 가정로 334")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
            .andDo(print())
            .andExpect(status().isOk())
            .andDo(document("search-regions-geocode",
                queryParameters(
                    parameterWithName("fullAddress").description("조회할 주소")
                ),
                responseFields(
                    fieldWithPath("latitude").type(STRING).description("위도"),
                    fieldWithPath("longitude").type(STRING).description("경도")
                )
            ));
    }
}
