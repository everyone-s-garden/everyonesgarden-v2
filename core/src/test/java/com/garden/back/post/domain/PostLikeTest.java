package com.garden.back.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostLikeTest {

    @ParameterizedTest
    @MethodSource("provideInvalidPostLikeArguments")
    @DisplayName("PostLike 생성 시 유효하지 않은 인자 검증")
    void testInvalidPostLikeCreation(Long likesClickerId, Long postId) {
        assertThatThrownBy(() -> PostLike.create(likesClickerId, postId))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideInvalidPostLikeArguments() {
        return Stream.of(
            Arguments.of(null, 1L), // likesClickerId 유효성(null)
            Arguments.of(0L, 1L), // likesClickerId 유효성(0)
            Arguments.of(-1L, 1L), // likesClickerId 유효성(음수)
            Arguments.of(1L, null), // postId 유효성(null)
            Arguments.of(1L, 0L), // postId 유효성(0)
            Arguments.of(1L, -1L) // postId 유효성(음수)
        );
    }
}