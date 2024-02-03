package com.garden.back.domain.question;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType;

    @Column(name = "content")
    private String content;

    @Column(name = "question_answer")
    private String questionAnswer;

    private Question(String content, QuestionType questionType, String questionAnswer) {
        Assert.hasText(content, "내용을 입력해주세요.");
        Assert.notNull(questionType, "questionType은 null 허용이 안됩니다.");
        Assert.hasText(questionAnswer, "questionAnswer을 입력해 주세요.");
        this.content = content;
        this.questionType = questionType;
        this.questionAnswer = questionAnswer;
    }

    public static Question create(String content, QuestionType questionType, String questionAnswer) {
        return new Question(content, questionType, questionAnswer);
    }

    public void update(String content, QuestionType questionType, String questionAnswer) {
        Assert.hasText(content, "내용을 입력해주세요.");
        Assert.notNull(questionType, "questionType은 null 허용이 안됩니다.");
        Assert.hasText(questionAnswer, "questionAnswer을 입력해 주세요.");
        this.content = content;
        this.questionType = questionType;
        this.questionAnswer = questionAnswer;
    }
}
