package com.garden.back.report.service;

import com.garden.back.global.event.Events;
import com.garden.back.report.domain.GardenReport;
import com.garden.back.report.domain.GardenReportCreatedEvent;
import com.garden.back.report.repository.ReportRepository;
import com.garden.back.report.service.request.ReportGardenServiceRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional
    public Long reportGarden(Long reporterId, Long gardenId, ReportGardenServiceRequest request) {
        if(reportRepository.findByGardenIdAndReporterId(gardenId, reporterId).isPresent()) {
            throw new IllegalArgumentException("동일한 텃밭에 대해서는 신고가 한번만 가능합니다.");
        }

        GardenReport savedReport = reportRepository.save(request.toEntity(reporterId, gardenId));
        Events.raise(new GardenReportCreatedEvent(savedReport));
        return savedReport.getId();
    }
}
