package com.garden.back.api.question.request;

import com.garden.back.domain.question.QuestionType;

public record UpdateQuestionServiceRequest(
    Long id,
    String content,
    QuestionType questionType,
    String questionAnswer
) {
}
