package com.garden.back.feedback.request;

import com.garden.back.feedback.FeedbackType;
import com.garden.back.feedback.service.request.FeedbackCreateServiceRequest;
import com.garden.back.global.validation.EnumValue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record FeedbackCreateRequest(
    @EnumValue(enumClass = FeedbackType.class, message = "GARDEN, CROP, COMMUNITY, CHAT, MYPAGE, ETC 중 하나의 값을 입력해주세요.")
    String feedbackType,

    @NotEmpty @Size(max = 255, message = "255글자 이하의 내용만 작성이 가능합니다.")
    String content
) {
    public FeedbackCreateServiceRequest toServiceRequest(List<MultipartFile> multipartFiles, Long memberId) {
        return FeedbackCreateServiceRequest.of(content, multipartFiles, memberId, FeedbackType.valueOf(feedbackType));
    }
}
