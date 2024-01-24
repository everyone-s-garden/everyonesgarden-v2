package com.garden.back.review.request;

import com.garden.back.review.CropPostReview;
import com.garden.back.review.CropPostReviewType;

import java.util.List;
import java.util.stream.Collectors;

public record WriteCropPostReviewServiceRequest(
    Long cropPostId,
    List<CropPostReviewType> reviewType,
    Float reviewScore,
    Long reviewReceiverId,
    Long reviewerId
    
) {
    public CropPostReview toEntity() {
        return CropPostReview.create(cropPostId, reviewType.stream().collect(Collectors.toSet()), reviewScore, reviewerId, reviewReceiverId);
    }
}
