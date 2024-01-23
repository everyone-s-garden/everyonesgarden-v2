package com.garden.back.report.service;

import com.garden.back.report.repository.ReportRepository;
import com.garden.back.report.service.request.ReportCommentServiceRequest;
import com.garden.back.report.service.request.ReportCropPostServiceRequest;
import com.garden.back.report.service.request.ReportGardenServiceRequest;
import com.garden.back.report.service.request.ReportPostServiceRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Transactional
    public Long reportGarden(Long reporterId, Long gardenId, ReportGardenServiceRequest request) {
        if (reportRepository.existsByReporterIdAndGardenId(reporterId, gardenId)) {
            throw new IllegalArgumentException("동일한 텃밭에 대해서는 신고가 한번만 가능합니다.");
        }

        return reportRepository.saveGardenReport(request.toEntity(reporterId, gardenId));
    }

    @Transactional
    public Long reportComment(ReportCommentServiceRequest request) {
        if (reportRepository.existsByReporterIdAndCommentId(request.reporterId(), request.commentId())) {
            throw new IllegalArgumentException("동일한 댓글에 대해서는 신고가 한번만 가능합니다.");
        }

        return reportRepository.saveCommentReport(request.toEntity());
    }

    @Transactional
    public Long reportPost(ReportPostServiceRequest request) {
        if (reportRepository.existsByReporterIdAndPostId(request.reporterId(), request.postId())) {
            throw new IllegalArgumentException("동일한 커뮤니티 게시글에는 신고가 한번만 가능합니다.");
        }

        return reportRepository.savePostReport(request.toEntity());
    }

    @Transactional
    public Long reportCropPost(ReportCropPostServiceRequest request) {
        if (reportRepository.existsByReporterIdAndCropPostId(request.reporterId(), request.cropPostId())) {
            throw new IllegalArgumentException("동일한 작물 게시글에는 한개의 신고만 가능합니다.");
        }

        return reportRepository.saveCropPostReport(request.toEntity());
    }

}
