package com.garden.back.controller.garden;

import com.garden.back.ControllerTestSupport;
import com.garden.back.garden.dto.request.GardenByComplexesRequest;
import com.garden.back.garden.dto.request.GardenByNameRequest;
import com.garden.back.garden.dto.request.GardenCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GardenControllerTest extends ControllerTestSupport {

    @DisplayName("학원 이름 검색에 요청값에 대해 검증한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidGardenByNameRequest")
    void getGardensByName_invalidRequest(GardenByNameRequest gardenByNameRequest) throws Exception {
        mockMvc.perform(get("/v2/gardens")
                        .content(objectMapper.writeValueAsString(gardenByNameRequest)))
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

    @DisplayName("분양하고자 하는 텃밭을 등록할 때 요청값에 대해서 검증한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidGardenCreateRequest")
    void createGarden_invalidRequest(GardenCreateRequest request) throws Exception {
        mockMvc.perform(post("/v2/gardens")
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

    private static Stream<GardenCreateRequest> provideInvalidGardenCreateRequest() {
        return Stream.of(
                new GardenCreateRequest(
                        "별이네 텃밭",
                        "100",
                        "200",
                        "aactive",
                        "www.everygarden.me",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        127.138868,
                                true,
                                true,
                                true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023.12.01",
                        "2023.12.23",
                        "2023.12.01",
                        "2023.12.31"
                ),
                new GardenCreateRequest(
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "www.everygarden.me",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        137.444917,
                        127.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023.12.01",
                        "2023.12.23",
                        "2023.12.01",
                        "2023.12.31"
                ),
                new GardenCreateRequest(
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "www.everygarden.me",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        190.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023.12.01",
                        "2023.12.23",
                        "2023.12.01",
                        "2023.12.31"
                ),
                new GardenCreateRequest(
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "www.everygarden.me",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        127.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하",
                        "2023.12.01",
                        "2023.12.23",
                        "2023.12.01",
                        "2023.12.31"
                ),
                new GardenCreateRequest(
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "www.everygarden.me",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        127.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023-12-01",
                        "2023-12-23",
                        "2023.12.01",
                        "2023.12.31"
                ),
                new GardenCreateRequest(
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "www.everygarden.me",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        127.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023.12.01",
                        "2023.11.23",
                        "2023.12.01",
                        "2023.12.31"
                )
        );
    }

}
//@NotBlank
//String gardenName,
//@NotBlank
//String price,
//@NotBlank
//String size,
//@NotBlank
//@EnumValue(enumClass = GardenStatus.class)
//String gardenStatus,
//String linkForRequest,
//String contact,
//@NotBlank
//String address,
//@NotBlank
//@DecimalMin(value = "-90.0", message = "위도는 -90.0 보다 크거나 같아야 한다.")
//@DecimalMax(value = "90.0", message = "위도는 90.0보다 같거나 작아야 한다.")
//Double latitude,
//@NotBlank
//@DecimalMin(value = "-180.0", message = "경도는 -180.0 보다 크거나 같아야 한다.")
//@DecimalMax(value = "180.0", message = "경도는 180.0 보다 같거나 작아야 한다.")
//Double longitude,
//@NotNull
//Boolean isToilet,
//@NotNull
//Boolean isWaterway,
//@NotNull
//Boolean isEquipment,
//@NotBlank
//@Length(min = 10, message = "텃밭 설명은 최소 10글자입니다.")
//String gardenDescription,
//@ValidDate
//String recruitStartDate,
//@ValidDate
//String recruitEndDate,
//@ValidDate
//String useStartDate,
//@ValidDate
//String useEndDate