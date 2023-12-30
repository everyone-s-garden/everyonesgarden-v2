package com.garden.back.post.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PostTest {

    private Post post;

    @BeforeEach
    void setUp() {
        List<String> postUrls = new ArrayList<>();
        postUrls.add("http://example.com/image1.jpg");
        postUrls.add("http://example.com/image2.jpg");
        post = Post.create("Test Title", "Test Content", 1L, postUrls);
    }

    @Test
    @DisplayName("새로운 게시물 생성 테스트")
    void testCreatePost() {
        assertThat(post).isNotNull();
        assertThat(post.getTitle()).isEqualTo("Test Title");
        assertThat(post.getContent()).isEqualTo("Test Content");
        assertThat(post.getPostAuthorId()).isEqualTo(1L);
        assertThat(post.getPostImages()).hasSize(2);
    }

    @Test
    @DisplayName("게시물 업데이트 테스트")
    void testUpdatePost() {
        List<String> newUrls = new ArrayList<>();
        newUrls.add("http://example.com/image3.jpg");
        List<String> deletedUrls = new ArrayList<>();
        deletedUrls.add("http://example.com/image1.jpg");

        post.update("New Title", "New Content", 1L, deletedUrls, newUrls);

        assertThat(post.getTitle()).isEqualTo("New Title");
        assertThat(post.getContent()).isEqualTo("New Content");
        assertThat(post.getPostImages()).hasSize(2); // One added, one removed
    }

    @Test
    @DisplayName("좋아요 수 증가 테스트")
    void testIncreaseLikeCount() {
        post.increaseLikeCount();
        assertThat(post.getLikesCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("좋아요 수 감소 테스트")
    void testDecreaseLikeCount() {
        post.increaseLikeCount(); // 좋아요 1 증가
        post.decreaseLikeCount(); // 좋아요 1 감소
        assertThat(post.getLikesCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("좋아요 수 감소 예외 테스트 - 음수 불가")
    void testDecreaseLikeCountException() {
        assertThatThrownBy(() -> post.decreaseLikeCount())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("음수가 될 수 없습니다");
    }

    @Test
    @DisplayName("댓글 수 증가 테스트")
    void testIncreaseCommentCount() {
        post.increaseCommentCount();
        assertThat(post.getCommentsCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("댓글 수 감소 테스트")
    void testDecreaseCommentCount() {
        post.increaseCommentCount(); // 댓글 1 증가
        post.decreaseCommentCount(); // 댓글 1 감소
        assertThat(post.getCommentsCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("댓글 수 감소 예외 테스트 - 음수 불가")
    void testDecreaseCommentCountException() {
        assertThatThrownBy(() -> post.decreaseCommentCount())
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("음수가 될 수 없습니다");
    }

    @Test
    @DisplayName("게시물 업데이트 가능 여부 검증")
    void testValidateUpdatable() {
        List<String> addedImages = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            addedImages.add("http://example.com/image" + i + ".jpg");
        }

        assertThatThrownBy(() -> post.validateUpdatable(addedImages.size(), 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("10개의 이미지만 등록할 수 있습니다");
    }

    @Test
    @DisplayName("게시물 삭제 가능 여부 검증")
    void testValidateDeletable() {
        assertThatThrownBy(() -> post.validateDeletable(2L)) // 다른 사용자 ID
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("게시글의 작성자만 게시글을 삭제할 수 있습니다");
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPostArguments")
    @DisplayName("게시물 생성 시 유효하지 않은 인자 검증")
    void testInvalidPostCreation(String title, String content, Long postAuthorId) {
        assertThatThrownBy(() -> Post.create(title, content, postAuthorId, new ArrayList<>()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideInvalidPostArguments() {
        return Stream.of(
            Arguments.of("유효한 제목", "", 1L), // Invalid content (blank)
            Arguments.of("유효하지 않은 제목".repeat(250), "Valid Content", 1L), // Invalid title (too long)
            Arguments.of("유효한 제목", "Valid Content", -1L) // Invalid postAuthorId (negative)
        );
    }
}
