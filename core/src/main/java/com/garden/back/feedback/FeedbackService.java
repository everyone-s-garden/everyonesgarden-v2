package com.garden.back.feedback;

import com.garden.back.feedback.request.FeedbackCreateServiceRequest;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    public Long createFeedback(FeedbackCreateServiceRequest request) {

        return 1L;
    }
}
