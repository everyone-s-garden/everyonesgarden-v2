package com.garden.back.api.question;

import com.garden.back.api.question.request.CreateQuestionRequest;
import com.garden.back.api.question.request.UpdateQuestionRequest;
import com.garden.back.domain.question.QuestionType;
import com.garden.back.global.ControllerTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;

import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class QuestionControllerTest extends ControllerTestSupport {

    @DisplayName("질문 생성 유효하지 않은 내용 테스트")
    @ParameterizedTest
    @MethodSource("invalidCreateQuestionRequest")
    void createQuestionInvalidRequest(CreateQuestionRequest request) throws Exception {
        mockMvc.perform(post("/v1/admin/questions")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    private static Stream<CreateQuestionRequest> invalidCreateQuestionRequest() {
        return Stream.of(
            // questionContent가 빈 경우
            new CreateQuestionRequest("", QuestionType.CHAT, "답변"),
            new CreateQuestionRequest(" ", QuestionType.CHAT, "답변"),
            // questionType이 null인 경우
            new CreateQuestionRequest("내용", null, "답변"),
            // questionAnswer가 빈 경우
            new CreateQuestionRequest("내용", QuestionType.CHAT, ""),
            new CreateQuestionRequest("내용", QuestionType.CHAT, " ")
        );
    }

    @DisplayName("질문 업데이트 유효하지 않은 요청 테스트")
    @ParameterizedTest
    @MethodSource("invalidUpdateQuestionRequest")
    void updateQuestionInvalidRequest(UpdateQuestionRequest request) throws Exception {
        mockMvc.perform(patch("/v1/admin/questions/{id}", 1L)
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    private static Stream<UpdateQuestionRequest> invalidUpdateQuestionRequest() {
        return Stream.of(
            // content가 빈 경우
            new UpdateQuestionRequest("", QuestionType.CHAT, "답변"),
            new UpdateQuestionRequest(" ", QuestionType.CHAT, "답변"),
            // questionType이 null인 경우
            new UpdateQuestionRequest("내용", null, "답변"),
            // questionAnswer가 빈 경우
            new UpdateQuestionRequest("내용", QuestionType.CHAT, ""),
            new UpdateQuestionRequest("내용", QuestionType.CHAT, " ")
        );
    }

}