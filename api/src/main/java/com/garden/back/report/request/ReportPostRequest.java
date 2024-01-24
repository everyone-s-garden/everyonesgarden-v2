package com.garden.back.report.request;

import com.garden.back.global.validation.EnumValue;
import com.garden.back.report.domain.post.PostReportType;
import com.garden.back.report.service.request.ReportPostServiceRequest;
import jakarta.validation.constraints.NotNull;

public record ReportPostRequest(
    @NotNull(message = "신고 타입을 입력해주세요.")
    @EnumValue(enumClass = PostReportType.class, message = "SPAMMING, SWEAR_WORD, SENSATIONAL, PERSONAL_INFORMATION_EXPOSURE, OFFENSIVE_EXPRESSION 중 입력헤주세요")
    String reportType
) {
    public ReportPostServiceRequest toServiceRequest(Long postId, Long reporterId) {
        return new ReportPostServiceRequest(postId, reporterId, PostReportType.valueOf(reportType));
    }
}
