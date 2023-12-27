package com.garden.back.report.domain;

import com.garden.back.global.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Table(name = "reports")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "report_type", discriminatorType = DiscriminatorType.STRING)
@Getter
public abstract class Report extends BaseTimeEntity {

    protected Report() {}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporter_id")
    private Long reporterId;

    @Column(name = "content")
    private String content;

    protected Report(Long reporterId, String content) {
        if (StringUtils.length(content) > 255) throw new IllegalArgumentException("255글자를 초과한 내용은 입력이 불가능 합니다.");
        this.reporterId = validateReporterId(reporterId);
        this.content = content;
    }

    private Long validateReporterId(Long reporterId) {
        if (reporterId == null || reporterId <= 0) throw new IllegalArgumentException("신고자의 아이디는 null 또는 음수가 입력될 수 없습니다.");
        return reporterId;
    }
}
