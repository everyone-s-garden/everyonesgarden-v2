package com.garden.back.report.domain.post;

import com.garden.back.global.event.Events;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "post_reports")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporter_id")
    private Long reporterId;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "report_type")
    @Enumerated(EnumType.STRING)
    private PostReportType reportType;

    private PostReport(Long postId, Long reporterId, PostReportType postReportType) {
        this.postId = postId;
        this.reporterId = reporterId;
        this.reportType = postReportType;
        Events.raise(new PostReportCreateEvent(this));
    }
    public static PostReport create(Long postId, Long reporterId, PostReportType postReportType) {
        return new PostReport(postId, reporterId, postReportType);
    }
}
