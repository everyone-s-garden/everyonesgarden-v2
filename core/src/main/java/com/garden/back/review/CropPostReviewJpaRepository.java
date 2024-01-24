package com.garden.back.review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CropPostReviewJpaRepository extends JpaRepository<CropPostReview, Long> {
    boolean existsByCropPostIdAndReviewWriterId(Long cropPostId, Long reviewWriterId);
}
