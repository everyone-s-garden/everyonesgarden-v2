package com.garden.back.report.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@DiscriminatorValue("garden")
@Getter
public class GardenReport extends Report {

    protected GardenReport() {}

    @Enumerated(EnumType.STRING)
    @Column(name = "garden_report_type")
    private GardenReportType gardenReportType;

    @Column(name = "garden_id")
    private Long gardenId;

    private GardenReport(Long reporterId, String content, GardenReportType gardenReportType, Long gardenId) {
        super(reporterId, content);
        this.gardenReportType = gardenReportType;
        this.gardenId = validateGardenId(gardenId);
    }

    private Long validateGardenId(Long gardenId) {
        if (gardenId == null || gardenId <= 0) throw new IllegalArgumentException("텃밭의 아이디는 null 또는 음수가 입력될 수 없습니다.");
        return gardenId;
    }



    public static GardenReport create(Long reporterId, String content, GardenReportType reportType, Long gardenId) {
        return new GardenReport(reporterId, content, reportType, gardenId);
    }
}
