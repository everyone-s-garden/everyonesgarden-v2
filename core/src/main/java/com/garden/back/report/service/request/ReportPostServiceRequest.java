package com.garden.back.report.service.request;

import com.garden.back.report.domain.post.PostReport;
import com.garden.back.report.domain.post.PostReportType;

public record ReportPostServiceRequest(
    Long postId,
    Long reporterId,
    PostReportType postReportType
) {
    public PostReport toEntity() {
        return PostReport.create(postId, reporterId, postReportType);
    }
}
