package com.garden.back.feedback;

import com.garden.back.feedback.request.FeedbackCreateRequest;
import com.garden.back.feedback.service.FeedbackService;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/v1/feedbacks")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> createFeedback(
        @RequestPart(value = "images", required = false) List<MultipartFile> multipartFiles,
        @RequestPart(value = "texts", required = true) @Valid FeedbackCreateRequest feedbackCreateRequest,
        @CurrentUser LoginUser loginUser
        ) {
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(feedbackService.createFeedback(feedbackCreateRequest.toServiceRequest(multipartFiles, loginUser.memberId())))
            .toUri();
        return ResponseEntity.created(location).build();
    }
}
