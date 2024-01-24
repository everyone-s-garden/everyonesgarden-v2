package com.garden.back.report.request;

import com.garden.back.global.validation.EnumValue;
import com.garden.back.report.domain.comment.CommentReportType;
import com.garden.back.report.service.request.ReportCommentServiceRequest;
import jakarta.validation.constraints.NotNull;

public record ReportCommentRequest(
    @NotNull(message = "신고 타입을 입력해주세요.")
    @EnumValue(enumClass = CommentReportType.class, message = "SPAMMING, SWEAR_WORD, SENSATIONAL, PERSONAL_INFORMATION_EXPOSURE, OFFENSIVE_EXPRESSION 중 입력헤주세요")
    String reportType
) {

    public ReportCommentServiceRequest toServiceRequest(Long reporterId, Long commentsId) {
        return new ReportCommentServiceRequest(reporterId, commentsId, CommentReportType.valueOf(reportType));
    }
}
