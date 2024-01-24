package com.garden.back.review;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.CropPost;
import com.garden.back.crop.domain.TradeStatus;
import com.garden.back.crop.domain.TradeType;
import com.garden.back.crop.domain.repository.CropJpaRepository;
import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.review.request.WriteCropPostReviewServiceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Transactional
class ReviewServiceTest extends IntegrationTestSupport {

    @Autowired
    ReviewService reviewService;

    @Autowired
    CropJpaRepository cropJpaRepository;

    @Autowired
    CropPostReviewJpaRepository cropPostReviewJpaRepository;

    @DisplayName("작물 게시글의 리뷰를 작성한다.")
    @Test
    void writeCropPostReview() {
        //given
        CropPost cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 100000, false, TradeType.DIRECT_TRADE, List.of("https://abc.com"), 1L, 1L);
        cropPost.update("제목", "내용", CropCategory.FRUIT, 10000, true, TradeType.DIRECT_TRADE, Collections.emptyList(), Collections.emptyList(), 1L, TradeStatus.TRADED, 1L);
        cropPost.assignBuyer(2L);
        CropPost savedCropPost = cropJpaRepository.save(cropPost);
        WriteCropPostReviewServiceRequest request = new WriteCropPostReviewServiceRequest(savedCropPost.getId(), List.of(CropPostReviewType.GOOD_CONDITION), 1.0f, 1L, 2L);

        //when
        Long savedReviewId = reviewService.writeCropPostReview(request);
        CropPostReview savedReview = cropPostReviewJpaRepository.findById(savedReviewId).orElseThrow(() -> new AssertionError("리뷰 조회 실패"));

        //then
        assertThat(savedReview)
            .extracting(
                CropPostReview::getCropPostId,
                CropPostReview::getReviewTypes,
                CropPostReview::getReviewScore,
                CropPostReview::getReviewWriterId,
                CropPostReview::getReviewReceiverId
            )
            .containsExactly(
                request.cropPostId(),
                new HashSet<>(request.reviewType()),
                request.reviewScore(),
                request.reviewerId(),
                request.reviewReceiverId()
            );
    }

    @DisplayName("구매자 등록이 안된 게시글에는 리뷰 작성이 불가능하다.")
    @Test
    void writeCropPostReviewInvalidBuyer() {
        //given
        CropPost cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 100000, false, TradeType.DIRECT_TRADE, List.of("https://abc.com"), 1L, 1L);
        cropPost.update("제목", "내용", CropCategory.FRUIT, 10000, true, TradeType.DIRECT_TRADE, Collections.emptyList(), Collections.emptyList(), 1L, TradeStatus.TRADED, 1L);
        CropPost savedCropPost = cropJpaRepository.save(cropPost);
        WriteCropPostReviewServiceRequest request = new WriteCropPostReviewServiceRequest(savedCropPost.getId(), List.of(CropPostReviewType.GOOD_CONDITION), 1.0f, 1L, 2L);

        //when & then
        assertThatThrownBy(() -> reviewService.writeCropPostReview(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("구매자가 등록되지 않은 작물 게시글 입니다.");
    }

    @DisplayName("하나의 거래에는 하나의 리뷰만 작성할 수 있다.")
    @Test
    void writeCropPostReviewInvalidReviewer() {
        //given
        CropPost cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 100000, false, TradeType.DIRECT_TRADE, List.of("https://abc.com"), 1L, 1L);
        cropPost.update("제목", "내용", CropCategory.FRUIT, 10000, true, TradeType.DIRECT_TRADE, Collections.emptyList(), Collections.emptyList(), 1L, TradeStatus.TRADED, 1L);
        cropPost.assignBuyer(2L);
        CropPost savedCropPost = cropJpaRepository.save(cropPost);
        WriteCropPostReviewServiceRequest request = new WriteCropPostReviewServiceRequest(savedCropPost.getId(), List.of(CropPostReviewType.GOOD_CONDITION), 1.0f, 1L, 2L);
        reviewService.writeCropPostReview(request);

        //when & then
        assertThatThrownBy(() -> reviewService.writeCropPostReview(request))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("하나의 거래에는 한번의 리뷰만 등록 가능합니다.");
    }
}