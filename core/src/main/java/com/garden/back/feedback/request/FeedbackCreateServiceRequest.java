package com.garden.back.feedback.request;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record FeedbackCreateServiceRequest(
    String content,
    List<MultipartFile> images
) {
}
