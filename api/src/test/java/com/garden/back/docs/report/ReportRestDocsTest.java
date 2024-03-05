package com.garden.back.docs.report;

import com.garden.back.docs.RestDocsSupport;
import com.garden.back.report.ReportController;
import com.garden.back.report.domain.comment.CommentReportType;
import com.garden.back.report.domain.crop.CropPostReportType;
import com.garden.back.report.domain.garden.GardenReportType;
import com.garden.back.report.domain.post.PostReportType;
import com.garden.back.report.request.ReportCommentRequest;
import com.garden.back.report.request.ReportGardenRequest;
import com.garden.back.report.request.ReportPostRequest;
import com.garden.back.report.service.ReportService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReportRestDocsTest extends RestDocsSupport {

    ReportService reportService = mock(ReportService.class);

    @Override
    protected Object initController() {
        return new ReportController(reportService);
    }

    @DisplayName("텃밭에 대해 신고하는 API DOCS")
    @Test
    void reportGarden() throws Exception {
        Long gardenId = 1L;
        ReportGardenRequest request = sut.giveMeBuilder(ReportGardenRequest.class)
            .set("content", "허위로 등록된 텃밭 입니디.")
            .set("reportType", "FAKED_SALE")
                .sample();
        mockMvc.perform(post("/v1/reports/gardens/{gardenId}", gardenId)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("report-garden",
                pathParameters(
                    parameterWithName("gardenId").description("텃밭의 id")
                ),
                requestFields(
                    fieldWithPath("content").type(STRING).description("신고 내용"),
                    fieldWithPath("reportType").type(STRING)
                        .description("신고타입:"+Arrays.stream(GardenReportType.values())
                            .map(Enum::name)
                            .collect(Collectors.joining(", ")))
                ),
                responseHeaders(
                    headerWithName("Location").description("생성된 신고의 id를 포함한 url")
                )
            ));
    }

    @DisplayName("댓글 신고 api docs")
    @Test
    void reportComment() throws Exception {
        given(reportService.reportComment(any())).willReturn(1L);
        ReportCommentRequest request = new ReportCommentRequest("SPAMMING");

        mockMvc.perform(post("/v1/reports/comments/{commentId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("report-comments",
                pathParameters(
                    parameterWithName("commentId").description("댓글 id")
                ),
                requestFields(
                    fieldWithPath("reportType").type(STRING)
                        .description("신고 타입:"+Arrays.stream(CommentReportType.values())
                            .map(Enum::name)
                            .collect(Collectors.joining(", ")))
                ),
                responseHeaders(
                    headerWithName("Location").description("생성된 신고의 id를 포함한 url")
                )
            ));
    }

    @DisplayName("게시글 신고 api docs")
    @Test
    void reportPost() throws Exception {
        given(reportService.reportPost(any())).willReturn(1L);
        ReportPostRequest request = new ReportPostRequest("SPAMMING");

        mockMvc.perform(post("/v1/reports/posts/{postId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("report-posts",
                pathParameters(
                    parameterWithName("postId").description("게시글 id")
                ),
                requestFields(
                    fieldWithPath("reportType").type(STRING).description("신고 타입:"+Arrays.stream(PostReportType.values())
                        .map(Enum::name)
                        .collect(Collectors.joining(", ")))
                ),
                responseHeaders(
                    headerWithName("Location").description("생성된 신고의 id를 포함한 url")
                )
            ));
    }

    @DisplayName("작물 게시글 신고 api docs")
    @Test
    void reportCropPost() throws Exception {
        given(reportService.reportPost(any())).willReturn(1L);
        ReportPostRequest request = new ReportPostRequest("SPAMMING");

        mockMvc.perform(post("/v1/reports/crop-posts/{cropPostId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("report-crop-posts",
                pathParameters(
                    parameterWithName("cropPostId").description("작물 게시글 id")
                ),
                requestFields(
                    fieldWithPath("reportType").type(STRING).description("신고 타입:"+Arrays.stream(CropPostReportType.values())
                        .map(Enum::name)
                        .collect(Collectors.joining(", ")))
                ),
                responseHeaders(
                    headerWithName("Location").description("생성된 신고의 id를 포함한 url")
                )
            ));
    }
}
