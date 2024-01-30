package com.garden.back.docs.feedback;

import com.garden.back.docs.RestDocsSupport;
import com.garden.back.feedback.FeedbackController;
import com.garden.back.feedback.service.FeedbackService;
import com.garden.back.feedback.request.FeedbackCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.mock;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.multipart;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestPartFields;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FeedbackRestDocsTest extends RestDocsSupport {

    FeedbackService feedbackService = mock(FeedbackService.class);

    @Override
    protected Object initController() {
        return new FeedbackController(feedbackService);
    }

    @DisplayName("어플 불편 사항에 대해 피드백을 주는 API DOCS")
    @Test
    void reportGarden() throws Exception {
        FeedbackCreateRequest request = sut.giveMeBuilder(FeedbackCreateRequest.class)
            .set("content", "이 부분이 이상하네요.")
            .sample();
        MockMultipartFile firstImage = new MockMultipartFile(
            "images",
            "image1.png",
            "image/png",
            "image-files".getBytes()
        );

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "texts",
            "content",
            MediaType.APPLICATION_JSON_VALUE,
            objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(
                multipart("/v1/feedbacks")
                    .file(firstImage)
                    .file(mockMultipartFile)
            )
            .andDo(print())
            .andExpect(status().isCreated())
            .andDo(print())
            .andDo(document("create-feedback",
                requestPartFields("texts",
                    fieldWithPath("content").type(STRING).description("신고 내용")
                ),
                requestParts(
                    partWithName("texts").description("피드백 내용 texts에는 json 형식으로 위 part 필드들에 대해 요청해주시면 됩니다."),
                    partWithName("images").description("피드백 관련 스크린샷이나 이미지(10개까지 허옹)").optional()
                ),
                responseHeaders(
                    headerWithName("Location").description("생성된 피드백의 id를 포함한 url")
                )
            ));
    }
}
