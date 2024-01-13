package com.garden.back.crop.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CropPostTest {

    @ParameterizedTest
    @MethodSource("provideInvalidCropPostArguments")
    @DisplayName("CropPost 생성 시 유효하지 않은 인자 검증")
    void testInvalidCropPostCreation(String content, String title, CropCategory cropCategory, Integer price, Boolean priceProposal, TradeType tradeType, Long cropPostAuthorId, List<String> cropUrls) {
        assertThatThrownBy(() -> CropPost.create(content, title, cropCategory, price, priceProposal, tradeType, cropUrls, cropPostAuthorId))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> provideInvalidCropPostArguments() {
        return Stream.of(
            Arguments.of(null, "유효한 제목", CropCategory.FRUIT, 100, true, TradeType.DELIVERY_TRADE, 1L, Collections.singletonList("http://validurl.com")),
            Arguments.of("유효한 내용", null, CropCategory.FRUIT, 100, true, TradeType.DELIVERY_TRADE, 1L, Collections.singletonList("http://validurl.com")),
            Arguments.of("유효한 내용", "유효한 제목", null, 100, true, TradeType.DELIVERY_TRADE, 1L, Collections.singletonList("http://validurl.com")),
            Arguments.of("유효한 내용", "유효한 제목", CropCategory.VEGETABLE, null, true, TradeType.DELIVERY_TRADE, 1L, Collections.singletonList("http://validurl.com")),
            Arguments.of("유효한 내용", "유효한 제목", CropCategory.VEGETABLE, 100, null, TradeType.DELIVERY_TRADE, 1L, Collections.singletonList("http://validurl.com")),
            Arguments.of("유효한 내용", "유효한 제목", CropCategory.VEGETABLE, 100, true, null, 1L, Collections.singletonList("http://validurl.com")),
            Arguments.of("유효한 내용", "유효한 제목", CropCategory.VEGETABLE, 100, true, TradeType.DELIVERY_TRADE, null, Collections.singletonList("http://validurl.com"))
        );
    }

    @Test
    @DisplayName("update 메소드 유효성 검증")
    void testUpdateMethod() {
        // Arrange: 초기 CropPost 객체 생성
        Long authorId = 1L;
        CropPost post = CropPost.create("내용", "유효한 제목", CropCategory.FRUIT, 100, true, TradeType.DELIVERY_TRADE, List.of("http://validurl.com"), authorId);

        // Act & Assert: 작성자 ID가 다를 때
        assertThatThrownBy(() -> post.update("제목", "내용", CropCategory.FRUIT, 10000, false, TradeType.DIRECT_TRADE, List.of("http://validurl.com"), List.of("http://validurl.com"), 2L, TradeStatus.TRADED))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("자신이 작성한 작물 게시물만 수정이 가능합니다.");

        // 추가적인 테스트 케이스들을 구현...
    }

    @Test
    @DisplayName("increaseBookmarkCount 메소드 검증")
    void testIncreaseBookmarkCount() {
        // given
        CropPost post = CropPost.create("내용", "유효한 제목", CropCategory.FRUIT, 100, true, TradeType.DELIVERY_TRADE, List.of("http://validurl.com"), 1L);
        Long beforeCount = post.getBookMarkCount();

        // when
        post.increaseBookmarkCount();

        // then
        assertThat(post.getBookMarkCount()).isEqualTo(beforeCount + 1);
    }

    @Test
    @DisplayName("decreaseBookmarkCount 메소드 검증")
    void testDecreaseBookmarkCount() {
        // given
        CropPost post = CropPost.create("내용", "유효한 제목", CropCategory.FRUIT, 100, true, TradeType.DELIVERY_TRADE, List.of("http://validurl.com"), 1L);

        //when
        post.increaseBookmarkCount();
        post.decreaseBookmarkCount();

        //then
        assertThat(post.getBookMarkCount()).isEqualTo(0);

        // then
        assertThatThrownBy(post::decreaseBookmarkCount)
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("북마크의 개수가 0보다 아래일 수 없습니다.");
    }

    @Test
    @DisplayName("decreaseBookmarkCount 메소드 검증(값이 0보다 작아질 때")
    void testDecreaseBookmarkCountInvalid() {
        // given
        CropPost post = CropPost.create("내용", "유효한 제목", CropCategory.FRUIT, 100, true, TradeType.DELIVERY_TRADE, List.of("http://validurl.com"), 1L);

        // when & then
        assertThatThrownBy(post::decreaseBookmarkCount)
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("북마크의 개수가 0보다 아래일 수 없습니다.");
    }

    @Test
    @DisplayName("assignBuyer 메소드 검증")
    void testAssignBuyer() {
        // given
        CropPost post = CropPost.create("내용", "유효한 제목", CropCategory.FRUIT, 100, true, TradeType.DELIVERY_TRADE, List.of("http://validurl.com"), 1L);
        post.update("제목", "내용", CropCategory.FRUIT, 10000, false, TradeType.DIRECT_TRADE, List.of("http://validurl.com"), List.of("http://validurl.com"), 1L, TradeStatus.TRADED);
        Long buyerId = 2L;
        // when
        post.assignBuyer(buyerId);

        // then
        assertThat(post.getBuyerId()).isEqualTo(buyerId);
    }

    @Test
    @DisplayName("판매과 완료되지 않은 상품은 구매자를 할당할 수 없다.")
    void testAssignBuyerInvalid() {
        // given
        CropPost post = CropPost.create("내용", "유효한 제목", CropCategory.FRUIT, 100, true, TradeType.DELIVERY_TRADE, List.of("http://validurl.com"), 1L);
        Long buyerId = 2L;

        // when & then
        assertThatThrownBy(() -> post.assignBuyer(buyerId))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("거래 완료가 되지 않은 작물 게시글입니다.");
    }

}