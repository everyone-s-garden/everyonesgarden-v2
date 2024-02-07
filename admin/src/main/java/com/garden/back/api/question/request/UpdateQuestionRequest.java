package com.garden.back.api.question.request;

import com.garden.back.domain.question.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateQuestionRequest(
    @NotBlank(message = "내용을 입력해 주세요.")
    String content,

    @NotNull(message = "questionType을 입력해 주세요.")
    QuestionType questionType,

    @NotBlank(message = "질문에 대한 답변을 입력해 주세요.")
    String questionAnswer
) {
    public UpdateQuestionServiceRequest toServiceRequest(Long id) {
        return new UpdateQuestionServiceRequest(id, content, questionType, questionAnswer);
    }
}
