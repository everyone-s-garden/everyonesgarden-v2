package com.garden.back.domain.question.response;

import com.garden.back.domain.question.Question;
import com.garden.back.domain.question.QuestionType;

import java.util.List;

public record FindAllQuestions(
    List<QuestionInfo> questionInfos
) {
    public record QuestionInfo(
        Long id,
        String content,
        QuestionType questionType
    ) {
        public static QuestionInfo from(Question question) {
            return new QuestionInfo(question.getId(), question.getContent(), question.getQuestionType());
        }
    }

    public static FindAllQuestions from(List<QuestionInfo> questionInfo) {
        return new FindAllQuestions(questionInfo);
    }
}
