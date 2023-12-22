package com.garden.back.controller.garden;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.garden.back.garden.dto.request.GardenByComplexesRequest;
import com.garden.back.ControllerTestSupport;
import com.garden.back.garden.dto.request.GardenByNameRequest;
import com.garden.back.garden.dto.request.GardenGetAllRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class GardenControllerTest extends ControllerTestSupport {

    @DisplayName("학원 이름 검색에 요청값에 대해 검증한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidGardenByNameRequest")
    void getGardensByName_invalidRequest(GardenByNameRequest gardenByNameRequest) throws Exception {
        mockMvc.perform(get("/v2/gardens")
                        .content(objectMapper.writeValueAsString(gardenByNameRequest)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("텃밭 전체 검색 요청값에 대해 검증한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidGardenGetAllRequest")
    void getAllGardens_invalidRequest(GardenGetAllRequest gardenGetAllRequest) throws Exception {
        mockMvc.perform(get("/v2/gardens/all")
                        .content(objectMapper.writeValueAsString(gardenGetAllRequest)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("텃밭 위치에 따른 검색 요청값에 대해 검증한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidGardenByComplexesRequest")
    void getGardensByComplexes_invalidRequest(GardenByComplexesRequest request) throws Exception {
        mockMvc.perform(get("/v2/gardens/by-complexes")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    private static Stream<GardenByNameRequest> provideInvalidGardenByNameRequest() {
        return Stream.of(
                new GardenByNameRequest(
                        null,
                        0
                ),
                new GardenByNameRequest(
                        "",
                        0
                ),
                new GardenByNameRequest(
                        "도연이네 텃밭농장",
                        -1
                ),
                new GardenByNameRequest(
                        "도연이네 텃밭농장",
                        null
                )
        );
    }

    private static Stream<GardenGetAllRequest> provideInvalidGardenGetAllRequest() {
        return Stream.of(
                new GardenGetAllRequest(
                        -1,
                        0L
                ),
                new GardenGetAllRequest(
                        null,
                        0L
                ),
                new GardenGetAllRequest(
                        0,
                        -1L
                ),
                new GardenGetAllRequest(
                        0,
                        null
                )
        );
    }

    private static Stream<GardenByComplexesRequest> provideInvalidGardenByComplexesRequest() {
        return Stream.of(
                new GardenByComplexesRequest(
                        "",
                        0,
                        37.444917,
                        127.138868,
                        39.444917,
                        129.138868
                ),
                new GardenByComplexesRequest(
                        null,
                        0,
                        37.444917,
                        127.138868,
                        39.444917,
                        129.138868
                ),
                new GardenByComplexesRequest(
                        "ALL",
                        -1,
                        37.444917,
                        127.138868,
                        39.444917,
                        129.138868
                ),
                new GardenByComplexesRequest(
                        "ALL",
                        1,
                        137.444917,
                        127.138868,
                        39.444917,
                        129.138868
                ),
                new GardenByComplexesRequest(
                        "ALL",
                        1,
                        37.444917,
                        1127.138868,
                        39.444917,
                        129.138868
                )
        );
    }

}
