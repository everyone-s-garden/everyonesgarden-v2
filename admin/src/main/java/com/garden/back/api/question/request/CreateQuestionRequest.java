package com.garden.back.api.question.request;

import com.garden.back.domain.question.QuestionType;
import com.garden.back.domain.question.request.CreateQuestionServiceRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateQuestionRequest(
    @NotBlank(message = "질문 내용을 입력해 주세요.")
    String questionContent,

    @NotNull(message = "questionType을 입력해 주세요.")
    QuestionType questionType,

    @NotBlank(message = "질문에 대한 답변을 입력해 주세요.")
    String questionAnswer
) {
    public CreateQuestionServiceRequest toServiceRequest() {
        return new CreateQuestionServiceRequest(questionContent, questionType, questionAnswer);
    }
}
