package com.garden.back.feedback;

import com.garden.back.global.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "feedbacks")
@Getter
public class Feedback extends BaseTimeEntity {

    protected Feedback() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    private String content;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "feedback_type")
    @Enumerated(EnumType.STRING)
    private FeedbackType feedbackType;

    @Embedded
    private FeedbackImages feedbackImages;

    private Feedback(String content, Long memberId, FeedbackImages feedbackImages, FeedbackType feedbackType) {
        this.content = content;
        this.memberId = memberId;
        this.feedbackImages = feedbackImages;
        this.feedbackType = feedbackType;
    }

    public static Feedback create(String content, Long memberId, FeedbackImages feedbackImages, FeedbackType feedbackType) {
        return new Feedback(content, memberId, feedbackImages, feedbackType);
    }
}
