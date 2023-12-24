package com.garden.back.feedback.service;

import com.garden.back.feedback.Feedback;
import com.garden.back.feedback.FeedbackRepository;
import com.garden.back.feedback.service.request.FeedbackCreateServiceRequest;
import com.garden.back.global.image.ParallelImageUploader;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ParallelImageUploader parallelImageUploader;

    public FeedbackService(ParallelImageUploader parallelImageUploader, FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
        this.parallelImageUploader = parallelImageUploader;
    }

    @Transactional
    public Long createFeedback(FeedbackCreateServiceRequest request) {
        List<String> urls = parallelImageUploader.upload("feedback/", request.images());
        Feedback savedFeedback = feedbackRepository.save(request.toEntity(urls));
        return savedFeedback.getId();
    }
}
