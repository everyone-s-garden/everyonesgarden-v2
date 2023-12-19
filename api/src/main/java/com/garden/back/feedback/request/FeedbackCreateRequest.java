package com.garden.back.feedback.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record FeedbackCreateRequest(
    @NotEmpty @Size(max = 255, message = "255글자 이하의 내용만 작성이 가능합니다.") String content
) {
    public FeedbackCreateServiceRequest toServiceRequest(List<MultipartFile> multipartFiles) {
        return new FeedbackCreateServiceRequest(content, multipartFiles);
    }
}
