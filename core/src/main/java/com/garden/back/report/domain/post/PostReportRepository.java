package com.garden.back.report.domain.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {

    boolean existsByReporterIdAndPostId(Long reporterId, Long postId);

    Long countByPostId(Long postId);
}
