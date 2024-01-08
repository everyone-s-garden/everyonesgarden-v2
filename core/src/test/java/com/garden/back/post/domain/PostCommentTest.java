package com.garden.back.post.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostCommentTest {

    private static Stream<Arguments> provideInvalidPostCommentArguments() {
        return Stream.of(
            Arguments.of(1L, null, "Valid Content", 1L), // 작성자 아이디 유효(null)
            Arguments.of(1L, 0L, "Valid Content", 1L), // 작성자 아이디 유효(0)
            Arguments.of(1L, -1L, "Valid Content", 1L), // 작성자 아이디 유효(음수)
            Arguments.of(1L, 1L, null, 1L), // 내용 유효 (null)
            Arguments.of(1L, 1L, "a".repeat(256), 1L), // 내용 유효(256글자)
            Arguments.of(1L, 1L, "Valid Content", null), // 게시글 아이디 유효(null)
            Arguments.of(1L, 1L, "Valid Content", 0L), // 게시글 아이디 유효(0)
            Arguments.of(1L, 1L, "Valid Content", -1L) // 게시글 아이디 유효(음수)
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPostCommentArguments")
    @DisplayName("PostComment 생성 시 유효하지 않은 인자 검증")
    void testInvalidPostCommentCreation(Long parentCommentId, Long authorId, String content, Long postId) {
        assertThatThrownBy(() -> PostComment.create(parentCommentId, authorId, content, postId))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("댓글 업데이트 검증")
    void testUpdateComment() {
        Long authorId = 1L;
        PostComment comment = PostComment.create(null, authorId, "content", 1L);

        comment.update(authorId, "업데이트");
        assertThat(comment.getContent()).isEqualTo("업데이트");
    }

    @Test
    @DisplayName("댓글 업데이트 권한 검증")
    void testUpdateCommentWithInvalidAuthority() {
        Long authorId = 1L;
        PostComment comment = PostComment.create(null, authorId, "content", 1L);

        Long differentMemberId = 2L;
        assertThatThrownBy(() -> comment.update(differentMemberId, "업데이트"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("댓글 작성자에게 권한이 있습니다.");
    }

    @Test
    @DisplayName("댓글 삭제 권한 검증")
    void testValidateDeletable() {
        Long authorId = 1L;
        PostComment comment = PostComment.create(null, authorId, "Content", 1L);

        Long differentMemberId = 2L;
        assertThatThrownBy(() -> comment.validateDeletable(differentMemberId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("댓글 작성자에게 권한이 있습니다.");
    }

    @Test
    @DisplayName("좋아요 수 증가 검증")
    void testIncreaseLikeCount() {
        PostComment comment = PostComment.create(null, 1L, "Content", 1L);

        comment.increaseLikeCount();
        assertThat(comment.getLikesCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("좋아요 수 감소 검증")
    void testDecreaseLikeCount() {
        PostComment comment = PostComment.create(null, 1L, "Content", 1L);

        comment.increaseLikeCount();
        comment.decreaseLikeCount();
        assertThat(comment.getLikesCount()).isZero();
    }

    @Test
    @DisplayName("좋아요 수 음수 예외 검증")
    void testDecreaseLikeCountException() {
        PostComment comment = PostComment.create(null, 1L, "Content", 1L);

        assertThatThrownBy(comment::decreaseLikeCount)
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("좋아요의 갯수는 음수가 될 수 없습니다.");
    }
}