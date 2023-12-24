package com.garden.back.controller.report;

import com.garden.back.ControllerTestSupport;
import com.garden.back.report.request.ReportGardenRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReportControllerTest extends ControllerTestSupport {

    @DisplayName("유효하지 않은 텃밭에 대해 신고할 때 잘못된 요청으로 인해 BadRequest 응답을 반환한다.")
    @ParameterizedTest
    @MethodSource("invalidReportGardenParameters")
    void reportGarden(ReportGardenRequest request) throws Exception {
        Long gardenId = 1L;
        mockMvc.perform(post("/v1/reports/{gardenId}", gardenId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }

    static Stream<ReportGardenRequest> invalidReportGardenParameters() {
        return Stream.of(
            new ReportGardenRequest("", "FAKED_SALE"), // content가 비어 있는 경우
            new ReportGardenRequest(null, "FAKED_SALE"), // content가 null인 경우
            new ReportGardenRequest("허위 텃밭 입니다.", "INVALID_TYPE"), // reportType이 잘못된 값인 경우
            new ReportGardenRequest("허위 텃밭 입니다.", null) // reportType이 null인 경우
        );
    }
}