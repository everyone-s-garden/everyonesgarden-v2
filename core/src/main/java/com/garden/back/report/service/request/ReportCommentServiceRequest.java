package com.garden.back.report.service.request;

import com.garden.back.report.domain.comment.CommentReport;
import com.garden.back.report.domain.comment.CommentReportType;

public record ReportCommentServiceRequest(
    Long reporterId,
    Long commentId,
    CommentReportType reportType
) {
    public CommentReport toEntity() {
        return CommentReport.create(reporterId, commentId, reportType);
    }
}
