package com.garden.back.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentLikeTest {

    @ParameterizedTest
    @MethodSource("provideInvalidCommentLikeArguments")
    @DisplayName("CommentLike 생성 시 유효하지 않은 인자 검증")
    void testInvalidCommentLikeCreation(Long likesClickerId, Long commentId) {
        assertThatThrownBy(() -> CommentLike.create(likesClickerId, commentId))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideInvalidCommentLikeArguments() {
        return Stream.of(
            Arguments.of(null, 1L), // Invalid likesClickerId (null)
            Arguments.of(0L, 1L), // Invalid likesClickerId (zero)
            Arguments.of(-1L, 1L), // Invalid likesClickerId (negative)
            Arguments.of(1L, -1L) // Invalid commentId (negative)
            // commentId can be null, so no need to test for null
        );
    }
}