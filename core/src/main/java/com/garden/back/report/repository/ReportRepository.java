package com.garden.back.report.repository;

import com.garden.back.report.domain.comment.CommentReport;
import com.garden.back.report.domain.comment.CommentReportRepository;
import com.garden.back.report.domain.crop.CropPostReport;
import com.garden.back.report.domain.crop.CropPostReportRepository;
import com.garden.back.report.domain.garden.GardenReport;
import com.garden.back.report.domain.garden.GardenReportRepository;
import com.garden.back.report.domain.post.PostReport;
import com.garden.back.report.domain.post.PostReportRepository;
import org.springframework.stereotype.Component;

@Component
public class ReportRepository {
    private final PostReportRepository postReportRepository;
    private final CommentReportRepository commentReportRepository;
    private final CropPostReportRepository cropPostReportRepository;
    private final GardenReportRepository gardenReportRepository;

    public ReportRepository(
        PostReportRepository postReportRepository,
        CommentReportRepository commentReportRepository,
        CropPostReportRepository cropPostReportRepository,
        GardenReportRepository gardenReportRepository
    ) {
        this.postReportRepository = postReportRepository;
        this.commentReportRepository = commentReportRepository;
        this.cropPostReportRepository = cropPostReportRepository;
        this.gardenReportRepository = gardenReportRepository;
    }

    public boolean existsByReporterIdAndGardenId(Long reporterId, Long gardenId) {
        return gardenReportRepository.existsByReporterIdAndGardenId(reporterId, gardenId);
    }

    public Long saveGardenReport(GardenReport entity) {
        return gardenReportRepository.save(entity).getId();
    }

    public boolean existsByReporterIdAndCommentId(Long reporterId, Long commentId) {
        return commentReportRepository.existsByReporterIdAndCommentId(reporterId, commentId);
    }

    public Long saveCommentReport(CommentReport commentReport) {
        return commentReportRepository.save(commentReport).getId();
    }

    public boolean existsByReporterIdAndCropPostId(Long reporterId, Long cropPostId) {
        return cropPostReportRepository.existsByReporterIdAndCropPostId(reporterId, cropPostId);
    }

    public Long saveCropPostReport(CropPostReport cropPostReport) {
        return cropPostReportRepository.save(cropPostReport).getId();
    }

    public boolean existsByReporterIdAndPostId(Long reporterId, Long postId) {
        return postReportRepository.existsByReporterIdAndPostId(reporterId, postId);
    }

    public Long savePostReport(PostReport postReport) {
        return postReportRepository.save(postReport).getId();
    }
}
