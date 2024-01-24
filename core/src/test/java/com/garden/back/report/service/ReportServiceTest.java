package com.garden.back.report.service;

import com.garden.back.crop.domain.CropCategory;
import com.garden.back.crop.domain.CropPost;
import com.garden.back.crop.domain.TradeType;
import com.garden.back.crop.domain.repository.CropJpaRepository;
import com.garden.back.global.IntegrationTestSupport;
import com.garden.back.post.domain.Post;
import com.garden.back.post.domain.PostComment;
import com.garden.back.post.domain.repository.PostCommentRepository;
import com.garden.back.post.domain.repository.PostRepository;
import com.garden.back.report.domain.comment.CommentReport;
import com.garden.back.report.domain.comment.CommentReportRepository;
import com.garden.back.report.domain.comment.CommentReportType;
import com.garden.back.report.domain.crop.CropPostReport;
import com.garden.back.report.domain.crop.CropPostReportRepository;
import com.garden.back.report.domain.crop.CropPostReportType;
import com.garden.back.report.domain.garden.GardenReportRepository;
import com.garden.back.report.domain.garden.GardenReportType;
import com.garden.back.report.domain.post.PostReport;
import com.garden.back.report.domain.post.PostReportRepository;
import com.garden.back.report.domain.post.PostReportType;
import com.garden.back.report.repository.ReportRepository;
import com.garden.back.report.service.request.ReportCommentServiceRequest;
import com.garden.back.report.service.request.ReportCropPostServiceRequest;
import com.garden.back.report.service.request.ReportGardenServiceRequest;
import com.garden.back.report.service.request.ReportPostServiceRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
class ReportServiceTest extends IntegrationTestSupport {

    @Autowired
    ReportService reportService;

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    GardenReportRepository gardenReportRepository;

    @Autowired
    CommentReportRepository commentReportRepository;

    @Autowired
    PostCommentRepository postCommentRepository;

    @Autowired
    PostReportRepository postReportRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    CropPostReportRepository cropPostReportRepository;

    @Autowired
    CropJpaRepository cropJpaRepository;

    @DisplayName("유효하지 않은 텃밭에 대해 신고할 수 있다.")
    @Test
    void reportGarden() {
        // given
        Long reporterId = 1L;
        Long gardenId = 1L;
        String content = "허위 매물 입니다.";
        GardenReportType reportType = GardenReportType.FAKED_SALE;
        ReportGardenServiceRequest request = sut.giveMeBuilder(ReportGardenServiceRequest.class)
            .set("content", content)
            .set("reportType", reportType)
            .sample();

        // when
        Long savedId = reportService.reportGarden(reporterId, gardenId, request);

        // then
        assertThat(savedId).isNotNull();

        gardenReportRepository.findById(savedId).ifPresentOrElse(gardenReport -> {
            assertAll(
                () -> assertThat(gardenReport.getReporterId()).isEqualTo(reporterId),
                () -> assertThat(gardenReport.getGardenId()).isEqualTo(gardenId),
                () -> assertThat(gardenReport.getContent()).isEqualTo(content),
                () -> assertThat(gardenReport.getGardenReportType()).isEqualTo(reportType)
            );
        }, () -> fail("조회 실패"));
    }

    @DisplayName("동일한 사용자가 동일한 정원에 대해 두번 신고할 수 없다.")
    @Test
    void duplicateGardenReport() {
        // given
        Long reporterId = 1L;
        Long gardenId = 1L;
        String content = "허위 매물 입니다.";
        GardenReportType reportType = GardenReportType.FAKED_SALE;
        ReportGardenServiceRequest request = sut.giveMeBuilder(ReportGardenServiceRequest.class)
            .set("content", content)
            .set("reportType", reportType)
            .sample();

        // when
        reportService.reportGarden(reporterId, gardenId, request);

        // then
        assertThrows(IllegalArgumentException.class, () -> {
            reportService.reportGarden(reporterId, gardenId, request);
        });
    }

    @DisplayName("댓글을 신고한다.")
    @Test
    void reportComment() {
        //given
        PostComment postComment = PostComment.create(1L, 1L, "내용", 1L);
        postCommentRepository.save(postComment);

        //when
        ReportCommentServiceRequest request = new ReportCommentServiceRequest(1L ,1L, CommentReportType.SPAMMING);
        Long savedReportId = reportService.reportComment(request);

        //then
        CommentReport commentReport = commentReportRepository.findById(savedReportId).orElseThrow(() -> new AssertionError("신고 조회 실패"));
        assertThat(commentReport.getReportType()).isEqualTo(CommentReportType.SPAMMING);
    }

    @DisplayName("같은 댓글을 두번 신고할 수 없다.")
    @Test
    void reportCommentInvalid() {
        //given
        PostComment postComment = PostComment.create(1L, 1L, "내용", 1L);
        postCommentRepository.save(postComment);

        //when & then
        ReportCommentServiceRequest request = new ReportCommentServiceRequest(1L ,1L, CommentReportType.SPAMMING);
        assertThrows(IllegalArgumentException.class, () -> {
            reportService.reportComment(request);
            reportService.reportComment(request);
        });
    }

    @DisplayName("게시글을 신고한다.")
    @Test
    void reportPost() {
        //given
        Post post = Post.create("제목", "내용", 1L, List.of("https://abc.com"));
        postRepository.save(post);

        //when
        ReportPostServiceRequest request = new ReportPostServiceRequest(1L ,1L, PostReportType.SPAMMING);
        Long savedReportId = reportService.reportPost(request);

        //then
        PostReport postReport = postReportRepository.findById(savedReportId).orElseThrow(() -> new AssertionError("신고 조회 실패"));
        assertThat(postReport.getReportType()).isEqualTo(PostReportType.SPAMMING);
    }

    @DisplayName("같은 게시글을 두번 신고할 수 없다.")
    @Test
    void reportPostInvalid() {
        //given
        Post post = Post.create("제목", "내용", 1L, List.of("https://abc.com"));
        postRepository.save(post);

        //when & then
        ReportPostServiceRequest request = new ReportPostServiceRequest(1L ,1L, PostReportType.SPAMMING);
        assertThrows(IllegalArgumentException.class, () -> {
            reportService.reportPost(request);
            reportService.reportPost(request);
        });
    }

    @DisplayName("작물 게시글을 신고한다.")
    @Test
    void reportCropPost() {
        //given
        CropPost cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 10000, false, TradeType.DIRECT_TRADE, List.of("https://abc.com"), 1L, 1L);
        cropJpaRepository.save(cropPost);

        //when
        ReportCropPostServiceRequest request = new ReportCropPostServiceRequest(1L ,1L, CropPostReportType.SPAMMING);
        Long savedReportId = reportService.reportCropPost(request);

        //then
        CropPostReport cropPostReport = cropPostReportRepository.findById(savedReportId).orElseThrow(() -> new AssertionError("신고 조회 실패"));
        assertThat(cropPostReport.getReportType()).isEqualTo(CropPostReportType.SPAMMING);
    }

    @DisplayName("같은 작물 게시글을 두버 신고할 수 없다.ㅌ")
    @Test
    void reportCropPostInvalid() {
        //given
        CropPost cropPost = CropPost.create("내용", "제목", CropCategory.FRUIT, 10000, false, TradeType.DIRECT_TRADE, List.of("https://abc.com"), 1L, 1L);
        cropJpaRepository.save(cropPost);

        //when & then
        ReportCropPostServiceRequest request = new ReportCropPostServiceRequest(1L ,1L, CropPostReportType.SPAMMING);
        assertThrows(IllegalArgumentException.class, () -> {
            reportService.reportCropPost(request);
            reportService.reportCropPost(request);
        });
    }
}