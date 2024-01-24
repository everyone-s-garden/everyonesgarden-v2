package com.garden.back.review;

import com.garden.back.global.LocationBuilder;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.review.request.CreateCropReviewRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/crops/{cropPostId}")
    ResponseEntity<Void> createCropReviews(
        @PathVariable("cropPostId") Long cropPostId,
        @RequestBody @Valid CreateCropReviewRequest request,
        @CurrentUser LoginUser loginUser
        ) {
        URI uri = LocationBuilder.buildLocation(reviewService.writeCropPostReview(request.toServiceRequest(cropPostId, loginUser.memberId())));
       return ResponseEntity.created(uri).build();
    }
}
