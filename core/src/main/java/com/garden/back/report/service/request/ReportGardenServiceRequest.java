package com.garden.back.report.service.request;

import com.garden.back.report.ReportType;

public record ReportGardenServiceRequest(
    String content,
    ReportType reportType
) {
}
