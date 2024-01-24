package com.garden.back.report;

import com.garden.back.global.validation.EnumValue;
import com.garden.back.report.domain.crop.CropPostReportType;
import com.garden.back.report.service.request.ReportCropPostServiceRequest;
import jakarta.validation.constraints.NotNull;

public record ReportCropPostRequest(
    @NotNull(message = "신고 타입을 입력해주세요.")
    @EnumValue(enumClass = CropPostReportType.class, message = "SPAMMING, SWEAR_WORD, SENSATIONAL, PERSONAL_INFORMATION_EXPOSURE, OFFENSIVE_EXPRESSION 중 입력헤주세요")
    String reportType
) {
    public ReportCropPostServiceRequest toServiceRequest(Long cropPostId, Long reporterId) {
        return new ReportCropPostServiceRequest(cropPostId, reporterId, CropPostReportType.valueOf(reportType));
    }
}
