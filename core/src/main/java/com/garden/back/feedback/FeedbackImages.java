package com.garden.back.feedback;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Embeddable
public class FeedbackImages {

    protected FeedbackImages() {}

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "feedback_id")
    private List<FeedbackImage> images = new ArrayList<>();

    public List<FeedbackImage> getImages() {
        return Collections.unmodifiableList(images);
    }

    private FeedbackImages(List<FeedbackImage> feedbackImages) {
        if (feedbackImages.size() > 10) {
            throw new IllegalArgumentException("피드백 사진은 10장까지 등록이 가능합니다.");
        }
        feedbackImages.forEach(feedbackImage -> images.add(feedbackImage));
    }

    public static FeedbackImages from(List<FeedbackImage> images) {
        return new FeedbackImages(images);
    }
}
