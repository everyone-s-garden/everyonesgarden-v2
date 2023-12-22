package com.garden.back.feedback;

import com.garden.back.global.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

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

    @Embedded
    private FeedbackImages feedbackImages;

    private Feedback(String content, Long memberId, FeedbackImages feedbackImages) {
        this.content = content;
        this.memberId = memberId;
        this.feedbackImages = feedbackImages;
    }

    public static Feedback create(String content, Long memberId, FeedbackImages feedbackImages) {
        return new Feedback(content, memberId, feedbackImages);
    }
}
