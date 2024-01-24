package com.garden.back.review.request;

import com.garden.back.review.CropPostReviewType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateCropReviewRequest(
    @NotNull(message = "리뷰 점수는 필수 항목입니다.")
    @DecimalMin(value = "0.0", message = "리뷰 점수는 0.0 이상이어야 합니다.")
    @DecimalMax(value = "5.0", message = "리뷰 점수는 5.0 이하이어야 합니다.")
    Float reviewScore,

    @NotEmpty(message = "리뷰 타입 목록은 비어있을 수 없습니다.")
    List<@NotNull(message = "리뷰 타입은 null일 수 없습니다.") CropPostReviewType> reviewTypes,

    @NotNull(message = "리뷰 받는 사용자의 아이디를 입력해주세요.")
    Long reviewReceiverId
) {
    public WriteCropPostReviewServiceRequest toServiceRequest(Long cropPostId, Long reviewerId) {
        return new WriteCropPostReviewServiceRequest(cropPostId, reviewTypes, reviewScore, reviewReceiverId, reviewerId);
    }
}
