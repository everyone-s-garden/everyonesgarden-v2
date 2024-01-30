package com.garden.back.review;

public record CropPostReviewCreateEvent(
    Long reviewReceiverId,
    Float reviewScore
) {
    public static CropPostReviewCreateEvent to(CropPostReview cropPostReview) {
        return new CropPostReviewCreateEvent(
            cropPostReview.getReviewReceiverId(),
            cropPostReview.getReviewScore()
        );
    }
}
