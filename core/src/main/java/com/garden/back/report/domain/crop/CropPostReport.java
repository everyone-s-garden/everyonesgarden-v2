package com.garden.back.report.domain.crop;

import com.garden.back.global.event.Events;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "crop_post_reports")
public class CropPostReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporter_id")
    private Long reporterId;

    @Column(name = "crop_post_id")
    private Long cropPostId;

    @Column(name = "report_type")
    @Enumerated(EnumType.STRING)
    private CropPostReportType reportType;

    private CropPostReport(Long cropPostId, Long reporterId, CropPostReportType reportType) {
        this.cropPostId = cropPostId;
        this.reporterId = reporterId;
        this.reportType = reportType;
        Events.raise(new CropPostReportCreateEvent(this));
    }

    public static CropPostReport create(Long cropPostId, Long reporterId, CropPostReportType reportType) {
        return new CropPostReport(cropPostId, reporterId, reportType);
    }
}
