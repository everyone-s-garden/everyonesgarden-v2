package com.garden.back.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class PostImageTest {

    @ParameterizedTest
    @MethodSource("provideInvalidPostImageArguments")
    @DisplayName("PostImage 생성 시 유효하지 않은 인자 검증")
    void testInvalidPostImageCreation(String imageUrl, Post post) {
        assertThatThrownBy(() -> PostImage.create(imageUrl, post))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideInvalidPostImageArguments() {
        Post mockPost = mock(Post.class);

        return Stream.of(
            Arguments.of(null, mockPost), // imageUrl 유효성(null)
            Arguments.of("http://example.com/image.jpg".repeat(256), mockPost), // imageUrl 유효성(길이가 김)
            Arguments.of("http://example.com/image.jpg", null), // Post가 null
            Arguments.of("invalid-url", mockPost) // 잘못된 URL 형식
        );
    }

}