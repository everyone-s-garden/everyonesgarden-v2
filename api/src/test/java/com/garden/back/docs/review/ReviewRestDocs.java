package com.garden.back.docs.review;

import com.garden.back.docs.RestDocsSupport;
import com.garden.back.review.CropPostReviewType;
import com.garden.back.review.ReviewController;
import com.garden.back.review.ReviewService;
import com.garden.back.review.request.CreateCropReviewRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.JsonFieldType.ARRAY;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ReviewRestDocs extends RestDocsSupport {
    ReviewService reviewService = mock(ReviewService.class);

    @Override
    protected Object initController() {
        return new ReviewController(reviewService);
    }

    @DisplayName("작물 게시글 리뷰 api docs")
    @Test
    void createCropReviews() throws Exception {
        given(reviewService.writeCropPostReview(any())).willReturn(1L);
        CreateCropReviewRequest request = new CreateCropReviewRequest(1.0f, List.of(CropPostReviewType.GOOD_CONDITION), 2L);

        mockMvc.perform(post("/v1/reviews/crops/{cropPostId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("create-crop-reviews",
                pathParameters(
                    parameterWithName("cropPostId").description("작물 게시글의 id")
                ),
                requestFields(
                    fieldWithPath("reviewScore").type(NUMBER).description("리뷰 점수(별점 5개는 1, 4개는 0.5, 3개는 0, 2개는 -0.5, 1개는 -1.0)"),
                    fieldWithPath("reviewTypes").type(ARRAY).description("리뷰 타입 목록"),
                    fieldWithPath("reviewReceiverId").type(NUMBER).description("리뷰 받은 이용자 아이디")
                ),
                responseHeaders(
                    headerWithName("Location").description("생성된 리뷰의 id")
                )
            ));

    }
}
