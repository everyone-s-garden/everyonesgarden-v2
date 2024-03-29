package com.garden.back.report.service.request;

import com.garden.back.report.domain.garden.GardenReport;
import com.garden.back.report.domain.garden.GardenReportType;

public record ReportGardenServiceRequest(
    String content,
    GardenReportType reportType
) {
    public GardenReport toEntity(Long reporterId, Long gardenId) {
        return GardenReport.create(reporterId, content, reportType, gardenId);
    }
}
