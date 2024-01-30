package com.garden.back.report.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentReportRepository extends JpaRepository<CommentReport, Long> {

    boolean existsByReporterIdAndCommentId(Long reporterId, Long commentId);

    Long countByCommentId(Long commentId);
}
