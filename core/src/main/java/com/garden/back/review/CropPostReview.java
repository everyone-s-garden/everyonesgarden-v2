package com.garden.back.review;

import com.garden.back.global.event.Events;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "crop_post_reviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CropPostReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "crop_post_id")
    private Long cropPostId;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "crop_post_review_types",
        joinColumns = @JoinColumn(name = "crop_post_review_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "review_type")
    private Set<CropPostReviewType> reviewTypes;

    @Column(name = "review_score")
    private Float reviewScore;

    @Column(name = "review_receiver_id")
    private Long reviewReceiverId;

    @Column(name = "review_writer_id")
    private Long reviewWriterId;


    private CropPostReview(Long cropPostId, Set<CropPostReviewType> cropPostReviewTypes, Float reviewScore, Long reviewWriterId, Long reviewReceiverId) {
        this.cropPostId = cropPostId;
        this.reviewTypes = cropPostReviewTypes;
        this.reviewScore = reviewScore;
        this.reviewWriterId = reviewWriterId;
        this.reviewReceiverId = reviewReceiverId;
        Events.raise(new CropPostReviewCreateEvent(this));
    }

    public static CropPostReview create(Long cropPostId, Set<CropPostReviewType> cropPostReviewType, Float reviewScore, Long reviewWriterId, Long reviewReceiverId) {
        return new CropPostReview(cropPostId, cropPostReviewType, reviewScore, reviewWriterId, reviewReceiverId);
    }
}
