package com.garden.back.review;

import com.garden.back.crop.domain.repository.CropJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class CropPostReviewRepository {
    private final CropPostReviewJpaRepository cropPostReviewJpaRepository;
    private final CropJpaRepository cropJpaRepository;

    public CropPostReviewRepository(CropPostReviewJpaRepository cropPostReviewJpaRepository, CropJpaRepository cropJpaRepository) {
        this.cropPostReviewJpaRepository = cropPostReviewJpaRepository;
        this.cropJpaRepository = cropJpaRepository;
    }

    public boolean existsByIdAndAndBuyerIdIsNull(Long id) {
        return cropJpaRepository.existsByIdAndAndBuyerIdIsNull(id);
    }

    public Long saveCropPostReview(CropPostReview cropPostReview) {
        return cropPostReviewJpaRepository.save(cropPostReview).getId();
    }

    public boolean existsByCropPostIdAndReviewWriterId(Long cropPostId, Long reviewerId) {
        return cropPostReviewJpaRepository.existsByCropPostIdAndReviewWriterId(cropPostId, reviewerId);
    }
}
