package com.garden.back.controller.feedback;

import com.garden.back.ControllerTestSupport;
import com.garden.back.feedback.request.FeedbackCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FeedbackControllerTest extends ControllerTestSupport {

    @DisplayName("피드백을 줄 때 피드백 내용이 비어 있거나 255글자를 넘을 수 없다.")
    @ParameterizedTest
    @MethodSource("contentProvider")
    void createFeedback(FeedbackCreateRequest request) throws Exception {
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

        mockMvc.perform(multipart("/v1/feedbacks")
            .file(firstImage)
            .file(mockMultipartFile))
            .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> contentProvider() {
        return Stream.of(
            Arguments.of(new FeedbackCreateRequest("a".repeat(256))),
            Arguments.of(new FeedbackCreateRequest("")),
            Arguments.of(new FeedbackCreateRequest(null))
        );
    }

}