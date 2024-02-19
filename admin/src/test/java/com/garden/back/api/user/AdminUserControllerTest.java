package com.garden.back.api.user;

import com.garden.back.api.user.request.GetMemberByMannerGradeOrderRequest;
import com.garden.back.api.user.request.GetMemberByPostCountRequest;
import com.garden.back.domain.user.MemberMannerGrade;
import com.garden.back.global.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminUserControllerTest extends ControllerTestSupport {

    @DisplayName("회원 별 게시물 수 조회 유효하지 않은 요청 테스트")
    @ParameterizedTest
    @MethodSource("invalidGetMemberByPostCountRequest")
    void getMemberByPostCountInvalidRequest(GetMemberByPostCountRequest request) throws Exception {
        mockMvc.perform(get("/v1/admin/members/post-count")
                .param("offset", request.offset().toString())
                .param("limit", request.limit().toString()))
            .andExpect(status().isBadRequest());
    }

    private static Stream<GetMemberByPostCountRequest> invalidGetMemberByPostCountRequest() {
        return Stream.of(
            new GetMemberByPostCountRequest(-1, 10), // offset이 음수인 경우
            new GetMemberByPostCountRequest(0, 0),   // limit이 0인 경우
            new GetMemberByPostCountRequest(0, -1)   // limit이 음수인 경우
        );
    }


    @DisplayName("회원 별 게시물 수 조회 유효하지 않은 요청 테스트")
    @ParameterizedTest
    @MethodSource("invalidGetMemberByMemberMannerGrade")
    void getMemberByMannerGradeOrder(GetMemberByMannerGradeOrderRequest request) throws Exception {
        String memberMannerGrade = null;
        if (request.memberMannerGrade() != null) {
            memberMannerGrade = request.memberMannerGrade().name();
        }

        mockMvc.perform(get("/v1/admin/members/member-manner-grade")
                .param("memberMannerGrade", memberMannerGrade)
                .param("offset", request.offset().toString())
                .param("limit", request.limit().toString()))
            .andExpect(status().isBadRequest());
    }

    private static Stream<GetMemberByMannerGradeOrderRequest> invalidGetMemberByMemberMannerGrade() {
        return Stream.of(
            new GetMemberByMannerGradeOrderRequest(MemberMannerGrade.SEED, -1, 10), // offset이 음수인 경우
            new GetMemberByMannerGradeOrderRequest(MemberMannerGrade.SEED, 0, 0),   // limit이 0인 경우
            new GetMemberByMannerGradeOrderRequest(MemberMannerGrade.SEED, 0, -1),   // limit이 음수인 경우
            new GetMemberByMannerGradeOrderRequest(null, 0, 1)
        );
    }
}