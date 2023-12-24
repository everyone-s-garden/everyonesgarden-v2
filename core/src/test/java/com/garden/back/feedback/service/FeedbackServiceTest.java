package com.garden.back.feedback.service;

import com.garden.back.feedback.Feedback;
import com.garden.back.feedback.FeedbackImages;
import com.garden.back.feedback.FeedbackRepository;
import com.garden.back.feedback.service.request.FeedbackCreateServiceRequest;
import com.garden.back.global.IntegrationTestSupport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class FeedbackServiceTest extends IntegrationTestSupport {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    FeedbackRepository feedbackRepository;

    @Test
    void createFeedback() {
        //given
        String expectedUrl = "https://kr.object.ncloudstorage.com/every-garden/images/feedback/download.jpg";
        MultipartFile multipartFile = new MockMultipartFile("test", expectedUrl.getBytes());
        int expectedSize = 1;
        FeedbackCreateServiceRequest request = sut.giveMeBuilder(FeedbackCreateServiceRequest.class)
            .size("images", 1)
            .set("images[0]", multipartFile)
            .sample();

        given(imageUploader.upload(any(), any())).willReturn(expectedUrl);

        //when
        Long savedId = feedbackService.createFeedback(request);

        //then
        Feedback actual = feedbackRepository.findById(savedId).orElseThrow();
        FeedbackImages feedbackImages = actual.getFeedbackImages();

        assertAll("Feedback and FeedbackImages",
            () -> assertThat(actual.getContent()).isEqualTo(request.content()),
            () -> assertThat(feedbackImages.getImages().size()).isEqualTo(expectedSize),
            () -> assertThat(feedbackImages.getImages().get(expectedSize - 1).getUrl()).isEqualTo(expectedUrl)
        );
    }
}