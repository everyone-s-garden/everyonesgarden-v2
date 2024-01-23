package com.garden.back.report.domain.comment;

import com.garden.back.global.jpa.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comment_reports")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentReport extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reporter_id")
    private Long reporterId;

    @Column(name = "comment_id")
    private Long commentId;

    @Column(name = "report_type")
    @Enumerated(EnumType.STRING)
    private CommentReportType reportType;

    private CommentReport(Long reporterId, Long commentId, CommentReportType commentReportType) {
        this.reporterId = reporterId;
        this.commentId = commentId;
        this.reportType = commentReportType;
    }

    public static CommentReport create(Long reporterId, Long commentId, CommentReportType commentReportType) {
        return new CommentReport(reporterId, commentId, commentReportType);
    }
}
