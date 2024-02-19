package com.garden.back.domain.question;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class QuestionTest {

    @ParameterizedTest
    @MethodSource("provideInvalidConstructorArguments")
    void testInvalidConstructor(String content, QuestionType questionType, String questionAnswer) {
        assertThrows(IllegalArgumentException.class, () -> {
            Question.create(content, questionType, questionAnswer);
        });
    }

    @ParameterizedTest
    @MethodSource("provideInvalidUpdateArguments")
    void testInvalidUpdate(Question question, String content, QuestionType questionType, String questionAnswer) {
        assertThrows(IllegalArgumentException.class, () -> {
            question.update(content, questionType, questionAnswer);
        });
    }

    private static Stream<Arguments> provideInvalidConstructorArguments() {
        return Stream.of(
            Arguments.of(null, QuestionType.CROP, "Some Answer"),
            Arguments.of("Some Content", null, "Some Answer"),
            Arguments.of("Some Content", QuestionType.CROP, null)
        );
    }

    private static Stream<Arguments> provideInvalidUpdateArguments() {
        Question validQuestion = Question.create("Valid Content", QuestionType.CROP, "Valid Answer");
        return Stream.of(
            Arguments.of(validQuestion, null, QuestionType.CROP, "Some Answer"),
            Arguments.of(validQuestion, "Some Content", null, "Some Answer"),
            Arguments.of(validQuestion, "Some Content", QuestionType.CROP, null)
        );
    }
}
