package com.garden.back.feedback.service;

import com.garden.back.feedback.Feedback;
import com.garden.back.feedback.FeedbackRepository;
import com.garden.back.feedback.service.request.FeedbackCreateServiceRequest;
import com.garden.back.global.image.ParallelImageUploader;
import com.garden.back.notification.NotificationSentApplication;
import com.garden.back.notification.domain.slack.SlackChannel;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ParallelImageUploader parallelImageUploader;
    private final NotificationSentApplication notificationSentApplication;

    public FeedbackService(ParallelImageUploader parallelImageUploader, FeedbackRepository feedbackRepository, NotificationSentApplication notificationSentApplication) {
        this.feedbackRepository = feedbackRepository;
        this.parallelImageUploader = parallelImageUploader;
        this.notificationSentApplication = notificationSentApplication;
    }

    @Transactional
    public Long createFeedback(FeedbackCreateServiceRequest request) {
        List<String> urls = parallelImageUploader.upload("feedback/", request.images());
        Feedback savedFeedback = feedbackRepository.save(request.toEntity(urls));
        notificationSentApplication.toSlack(SlackChannel.BOT, savedFeedback.getContent());
        return savedFeedback.getId();
    }
}
