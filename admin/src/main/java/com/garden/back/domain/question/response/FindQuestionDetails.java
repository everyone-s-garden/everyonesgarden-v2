package com.garden.back.domain.question.response;

import com.garden.back.domain.question.Question;

public record FindQuestionDetails(
    String content,
    String answer
) {
    public static FindQuestionDetails from(Question question) {
        return new FindQuestionDetails(question.getContent(), question.getQuestionAnswer());
    }
}
