package com.garden.back.report.domain.garden;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GardenReportRepository extends JpaRepository<GardenReport, Long> {
    boolean existsByReporterIdAndGardenId(Long reporterId, Long gardenId);
}
