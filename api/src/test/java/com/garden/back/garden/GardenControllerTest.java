package com.garden.back.garden;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.garden.back.ControllerTest;
import com.garden.back.garden.dto.request.GardenByNameRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class GardenControllerTest extends ControllerTest {

    @DisplayName("학원 이름 검색에 요청값에 대해 검증한다.")
    @ParameterizedTest
    @MethodSource("provideInvalidGardenByNameRequest")
    void getGardensByName_invalidRequest(GardenByNameRequest gardenByNameRequest) throws Exception {
        mockMvc.perform(get("/v2/garden")
                        .content(objectMapper.writeValueAsString(gardenByNameRequest)))
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

}
