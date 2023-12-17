package com.garden.back.report.request;

import com.garden.back.global.validation.EnumValue;
import com.garden.back.report.ReportType;
import com.garden.back.report.service.request.ReportGardenServiceRequest;
import jakarta.validation.constraints.NotEmpty;

public record ReportGardenRequest(
    @NotEmpty(message = "신고 내용을 입력해 주세요") String content,
    @NotEmpty(message = "신고 타입을 입력해 주세요") @EnumValue(enumClass = ReportType.class, message = "FAKED_SALE,SPAMMING, SWEAR_WORD, SENSATIONAL, PERSONAL_INFORMATION_EXPOSURE, COMMENTS 중 하나만 가능합니다.") String reportType
) {
    public ReportGardenServiceRequest toServiceRequest() {
        return new ReportGardenServiceRequest(content, ReportType.valueOf(reportType));
    }
}
