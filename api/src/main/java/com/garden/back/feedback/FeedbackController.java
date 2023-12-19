package com.garden.back.feedback;

import com.garden.back.feedback.request.FeedbackCreateRequest;
import com.garden.back.global.image.ImageUploader;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
        @RequestPart(value = "texts", required = true) @Valid FeedbackCreateRequest feedbackCreateRequest
    ) {
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(feedbackService.createFeedback(feedbackCreateRequest.toServiceRequest(multipartFiles)))
            .toUri();
        return ResponseEntity.created(location).build();
    }
}
