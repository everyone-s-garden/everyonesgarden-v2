package com.garden.back.report.service.request;

import com.garden.back.report.domain.crop.CropPostReport;
import com.garden.back.report.domain.crop.CropPostReportType;

public record ReportCropPostServiceRequest(
    Long cropPostId,
    Long reporterId,
    CropPostReportType reportType
) {
    public CropPostReport toEntity() {
        return CropPostReport.create(cropPostId, reporterId, reportType);
    }
}
