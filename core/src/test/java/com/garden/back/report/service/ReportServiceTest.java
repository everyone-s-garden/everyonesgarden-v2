package com.garden.back.report.service;

import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.report.model.GardenReport;
import com.garden.back.report.model.GardenReportType;
import com.garden.back.report.repository.ReportRepository;
import com.garden.back.report.service.request.ReportGardenServiceRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
class ReportServiceTest extends IntegrationTestSupport {

    @Autowired
    ReportService reportService;

    @Autowired
    ReportRepository reportRepository;

    @DisplayName("유효하지 않은 텃밭에 대해 신고할 수 있다.")
    @Test
    void reportGarden() {
        // given
        Long reporterId = 1L;
        Long gardenId = 1L;
        String content = "허위 매물 입니다.";
        GardenReportType reportType = GardenReportType.FAKED_SALE;
        ReportGardenServiceRequest request = sut.giveMeBuilder(ReportGardenServiceRequest.class)
            .set("content", content)
            .set("reportType", reportType)
            .sample();

        // when
        Long savedId = reportService.reportGarden(reporterId, gardenId, request);

        // then
        assertThat(savedId).isNotNull();

        reportRepository.findById(savedId).ifPresentOrElse(report -> {
            assertThat(report).isInstanceOf(GardenReport.class);
            GardenReport gardenReport = (GardenReport) report;

            assertAll(
                () -> assertThat(gardenReport.getReporterId()).isEqualTo(reporterId),
                () -> assertThat(gardenReport.getGardenId()).isEqualTo(gardenId),
                () -> assertThat(gardenReport.getContent()).isEqualTo(content),
                () -> assertThat(gardenReport.getGardenReportType()).isEqualTo(reportType)
            );
        }, () -> fail("조회 실패"));
    }

    @DisplayName("동일한 사용자가 동일한 정원에 대해 두번 신고할 수 없다.")
    @Test
    void duplicateGardenReport() {
        // given
        Long reporterId = 1L;
        Long gardenId = 1L;
        String content = "허위 매물 입니다.";
        GardenReportType reportType = GardenReportType.FAKED_SALE;
        ReportGardenServiceRequest request = sut.giveMeBuilder(ReportGardenServiceRequest.class)
            .set("content", content)
            .set("reportType", reportType)
            .sample();

        // when
        reportService.reportGarden(reporterId, gardenId, request);

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            reportService.reportGarden(reporterId, gardenId, request);
        });
    }
}