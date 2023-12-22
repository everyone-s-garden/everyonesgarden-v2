package com.garden.back.feedback.service.request;

import com.garden.back.feedback.Feedback;
import com.garden.back.feedback.FeedbackImage;
import com.garden.back.feedback.FeedbackImages;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record FeedbackCreateServiceRequest(
    String content,
    List<MultipartFile> images,
    Long memberId
) {
    public static FeedbackCreateServiceRequest of(String content, List<MultipartFile> images, Long memberId) {
        if (images.size() > 10) {
            throw new IllegalArgumentException("이미지는 10장까지 등록 가능합니다.");
        }

        return new FeedbackCreateServiceRequest(content, images, memberId);
    }

    public Feedback toEntity(List<String> feedbackImages) {
        List<FeedbackImage> feedbackImageList = feedbackImages.stream().map(FeedbackImage::create).toList();
        return Feedback.create(content, memberId, FeedbackImages.from(feedbackImageList));
    }

}
