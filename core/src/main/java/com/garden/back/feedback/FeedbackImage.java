package com.garden.back.feedback;

import com.garden.back.global.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "feedback_images")
@Getter
public class FeedbackImage extends BaseTimeEntity {

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
