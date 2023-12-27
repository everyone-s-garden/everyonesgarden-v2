package com.garden.back.report.repository;

import com.garden.back.report.domain.GardenReport;
import com.garden.back.report.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<GardenReport> findByGardenIdAndReporterId(Long gardenId, Long reporterId);
}
