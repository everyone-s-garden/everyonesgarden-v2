package com.garden.back.report.domain.garden;

import com.garden.back.global.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "garden_reports")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GardenReport extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporter_id")
    private Long reporterId;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "garden_report_type")
    private GardenReportType gardenReportType;

    @Column(name = "garden_id")
    private Long gardenId;

    private GardenReport(Long reporterId, String content, GardenReportType gardenReportType, Long gardenId) {
        if (StringUtils.length(content) > 255) throw new IllegalArgumentException("255글자를 초과한 내용은 입력이 불가능 합니다.");
        this.reporterId = validateReporterId(reporterId);
        this.content = content;
        this.gardenReportType = gardenReportType;
        this.gardenId = validateGardenId(gardenId);
    }

    private Long validateReporterId(Long reporterId) {
        if (reporterId == null || reporterId <= 0) {
            throw new IllegalArgumentException("신고자의 아이디는 null 또는 음수가 입력될 수 없습니다.");
        }
        return reporterId;
    }

    private Long validateGardenId(Long gardenId) {
        if (gardenId == null || gardenId <= 0) {
            throw new IllegalArgumentException("텃밭의 아이디는 null 또는 음수가 입력될 수 없습니다.");
        }
        return gardenId;
    }



    public static GardenReport create(Long reporterId, String content, GardenReportType reportType, Long gardenId) {
        return new GardenReport(reporterId, content, reportType, gardenId);
    }
}
