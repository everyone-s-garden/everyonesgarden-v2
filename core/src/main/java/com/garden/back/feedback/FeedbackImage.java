package com.garden.back.feedback;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

@Entity
@Table(name = "feedback_images")
@Getter
@ToString
public class FeedbackImage {

    protected FeedbackImage() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url")
    private String url;

    private FeedbackImage(String url) {
        this.url = url;
    }

    public static FeedbackImage create(String url) {
        return new FeedbackImage(url);
    }
}
