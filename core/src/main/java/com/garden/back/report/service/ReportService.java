package com.garden.back.report.service;

import com.garden.back.report.service.request.ReportGardenServiceRequest;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    public Long reportGarden(Long userId, Long gardenId, ReportGardenServiceRequest request) {
        return 1L;
    }
}
