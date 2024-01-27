package com.garden.back.report.domain.crop;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CropPostReportRepository extends JpaRepository<CropPostReport, Long> {

    boolean existsByReporterIdAndCropPostId(Long reporterId, Long cropPostId);

    Long countByCropPostId(Long id);
}
