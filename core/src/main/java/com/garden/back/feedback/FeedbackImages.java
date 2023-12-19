package com.garden.back.feedback;

import jakarta.persistence.*;
import lombok.ToString;

import java.util.*;

@Embeddable
@ToString
public class FeedbackImages {

    protected FeedbackImages() {}

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "feedback_id")
    private List<FeedbackImage> images = new ArrayList<>();

    public List<FeedbackImage> getImages() {
        return Collections.unmodifiableList(images);
    }

    private FeedbackImages(List<FeedbackImage> feedbackImages) {
        feedbackImages.forEach((feedbackImage) -> images.add(feedbackImage));
    }

    public static FeedbackImages from(List<FeedbackImage> images) {
        return new FeedbackImages(images);
    }
}
