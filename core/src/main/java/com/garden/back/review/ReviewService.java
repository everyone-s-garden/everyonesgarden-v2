package com.garden.back.review;

import com.garden.back.review.request.WriteCropPostReviewServiceRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private final CropPostReviewRepository cropPostReviewRepository;

    public ReviewService(CropPostReviewRepository cropPostReviewRepository) {
        this.cropPostReviewRepository = cropPostReviewRepository;
    }

    @Transactional
    public Long writeCropPostReview(WriteCropPostReviewServiceRequest request) {
        if (cropPostReviewRepository.existsByIdAndAndBuyerIdIsNull(request.cropPostId())) {
            throw new IllegalArgumentException("구매자가 등록되지 않은 작물 게시글 입니다.");
        }

        if (cropPostReviewRepository.existsByCropPostIdAndReviewWriterId(request.cropPostId(), request.reviewerId())) {
            throw new IllegalArgumentException("하나의 거래에는 한번의 리뷰만 등록 가능합니다.");
        }

        return cropPostReviewRepository.saveCropPostReview(request.toEntity());
    }
}
