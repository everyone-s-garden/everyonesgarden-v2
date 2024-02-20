package com.garden.back.controller.garden;

import com.garden.back.garden.controller.dto.request.*;
import com.garden.back.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @DisplayName("분양하고자 하는 텃밭을 수정할 때 요청값에 대해서 검증한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidGardenUpdateRequest")
    void updateGarden_invalidRequest(GardenUpdateRequest request) throws Exception {
        Long gardenId = 1L;
        mockMvc.perform(put("/v2/gardens/{gardenId}", gardenId)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("내가 가꾸는 텃밭을 등록할 때 요청값에 대해서 검증한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidMyManagedGardenCreateRequest")
    void createMyManagedGarden_invalidRequest(MyManagedGardenCreateRequest request) throws Exception {
        mockMvc.perform(put("/v2/gardens/my-managed")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("내가 가꾸는 텃밭을 수정할 때 myManagedGardenId path variable에 대해 음수와 0에 대해 검증한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void updateMyManagedGarden_invalidPathVariable(Long myManagedGardenId) throws Exception {
        mockMvc.perform(put("/v2/gardens/my-managed/{myManagedGardenId}", myManagedGardenId)
                        .content(objectMapper.writeValueAsString(myManagedGardenUpdateRequest())))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("내가 가꾸는 텃밭을 수정할 때 요청값에 대해 검증한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidMyManagedGardenUpdateRequest")
    void updated_invalidRequest(MyManagedGardenUpdateRequest request) throws Exception {
        Long myManagedGardenId = 1L;
        mockMvc.perform(put("/v2/gardens/my-managed/{myManagedGardenId}", myManagedGardenId)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("내가 가꾸는 텃밭을 상세 보기할 때 path variable이 음수 또는 0에 대해 검증한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    void getDetail_invalidPathVariable(Long myManagedGardenId) throws Exception {
        mockMvc.perform(get("/v2/gardens/my-managed/{myManagedGardenId}", myManagedGardenId))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("최근 등록된 텃밭을 조회할 때 memberId를 음수에 대해 검증한다.")
    @ParameterizedTest
    @ValueSource(longs = {-1L})
    void getRecentCreated_minusMemberId(Long memberId) throws Exception {
        mockMvc.perform(get("/v2/gardens/recent-created/{memberId}", memberId))
            .andExpect(status().is4xxClientError());
    }

    @DisplayName("최근 등록된 텃밭을 조회할 때 memberId를 null에 대해 검증한다.")
    @ParameterizedTest
    @NullSource
    void getRecentCreated_invalidMemberId(Long memberId) throws Exception {
        mockMvc.perform(get("/v2/gardens/recent-created/{memberId}", memberId))
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
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        127.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023.12.01",
                        "2023.12.23"
                ),
                new GardenCreateRequest(
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        137.444917,
                        127.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023.12.01",
                        "2023.12.23"
                ),
                new GardenCreateRequest(
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        190.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023.12.01",
                        "2023.12.23"
                ),
                new GardenCreateRequest(
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        127.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하",
                        "2023.12.01",
                        "2023.12.23"
                ),
                new GardenCreateRequest(
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        127.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023-12-01",
                        "2023-12-23"
                ),
                new GardenCreateRequest(
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        127.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023.12.01",
                        "2023.11.23"
                )
        );
    }

    private static Stream<GardenUpdateRequest> provideInvalidGardenUpdateRequest() {
        return Stream.of(
                new GardenUpdateRequest(
                        List.of("background.png"),
                        "별이네 텃밭",
                        "100",
                        "200",
                        "aactive",
                        "private",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        127.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023.12.01",
                        "2023.12.23"
                ),
                new GardenUpdateRequest(
                        List.of("background.png"),
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "PRIVATE",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        100.444917,
                        127.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023.12.01",
                        "2023.12.23"
                ),
                new GardenUpdateRequest(
                        List.of("background.png"),
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "PRIVATE",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        190.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023.12.01",
                        "2023.12.23"
                ),
                new GardenUpdateRequest(
                        List.of("background.png"),
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "PRIVATE",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        127.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하",
                        "2023.12.01",
                        "2023.12.23"
                ),
                new GardenUpdateRequest(
                        List.of("background.png"),
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "PRIVATE",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        127.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023-12-01",
                        "2023-12-23"
                ),
                new GardenUpdateRequest(
                        List.of("background.png"),
                        "별이네 텃밭",
                        "100",
                        "200",
                        "ACTIVE",
                        "PRIVATE",
                        "000-000-0000",
                        "인천광역시 서구 만수동 200",
                        37.444917,
                        127.138868,
                        true,
                        true,
                        true,
                        "화장실이 깨끗하고 흙이 좋아요",
                        "2023.12.01",
                        "2023.11.23"
                )
        );
    }

    private static Stream<MyManagedGardenCreateRequest> provideInvalidMyManagedGardenCreateRequest() {
        return Stream.of(
                new MyManagedGardenCreateRequest(
                        -1L,
                        "2023.12.01",
                        "2023.12.31"
                ),
                new MyManagedGardenCreateRequest(
                        1L,
                        "2023.12.01",
                        "2023.11.23"
                ),
                new MyManagedGardenCreateRequest(
                        1L,
                        "2023-12-01",
                        "2023-12-31"
                )
        );
    }

    private static MyManagedGardenUpdateRequest myManagedGardenUpdateRequest() {
        return new MyManagedGardenUpdateRequest(
                1L,
                "2023.12.01",
                "2023.12.31"
        );
    }

    private static Stream<MyManagedGardenUpdateRequest> provideInvalidMyManagedGardenUpdateRequest() {
        return Stream.of(
                new MyManagedGardenUpdateRequest(
                        -1L,
                        "2023.12.01",
                        "2023.12.31"
                ),
                new MyManagedGardenUpdateRequest(
                        1L,
                        "2023-12-01",
                        "2023-12-31"
                ),
                new MyManagedGardenUpdateRequest(
                        1L,
                        "2023.12.01",
                        "2023.11-30"
                )
        );
    }

}
