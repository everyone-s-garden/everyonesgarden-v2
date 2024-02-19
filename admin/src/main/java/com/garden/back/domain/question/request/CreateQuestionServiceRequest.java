package com.garden.back.domain.question.request;

import com.garden.back.domain.question.Question;
import com.garden.back.domain.question.QuestionType;

public record CreateQuestionServiceRequest(
    String content,
    QuestionType questionType,
    String questionAnswer
) {
    public Question toEntity() {
        return Question.create(content, questionType, questionAnswer);
    }
}
