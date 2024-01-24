package com.garden.back.report;

import com.garden.back.global.LocationBuilder;
import com.garden.back.global.loginuser.CurrentUser;
import com.garden.back.global.loginuser.LoginUser;
import com.garden.back.report.request.ReportCommentRequest;
import com.garden.back.report.request.ReportGardenRequest;
import com.garden.back.report.request.ReportPostRequest;
import com.garden.back.report.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/v1/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping(path = "/gardens/{gardenId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> reportGarden(
        @PathVariable("gardenId") Long gardenId,
        @RequestBody @Valid ReportGardenRequest request,
        @CurrentUser LoginUser loginUser
        ) {
        URI location = LocationBuilder.buildLocation(
            reportService.reportGarden(
                gardenId,
                loginUser.memberId(),
                request.toServiceRequest())
        );

        return ResponseEntity.created(location).build();
    }

    @PostMapping(path = "/comments/{commentsId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> reportComment(
        @PathVariable("commentsId") Long commentsId,
        @RequestBody @Valid ReportCommentRequest request,
        @CurrentUser LoginUser loginUser
    ) {
        URI location = LocationBuilder.buildLocation(
            reportService.reportComment(request.toServiceRequest(commentsId, loginUser.memberId()))
        );

        return ResponseEntity.created(location).build();
    }

    @PostMapping(path = "/posts/{postId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> reportPost(
        @PathVariable("postId") Long postId,
        @RequestBody @Valid ReportPostRequest request,
        @CurrentUser LoginUser loginUser
    ) {
        URI location = LocationBuilder.buildLocation(
            reportService.reportPost(request.toServiceRequest(postId, loginUser.memberId()))
        );

        return ResponseEntity.created(location).build();
    }

    @PostMapping(path = "/crop-posts/{cropPostId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> reportCropPost(
        @PathVariable("cropPostId") Long cropPostId,
        @RequestBody @Valid ReportCropPostRequest request,
        @CurrentUser LoginUser loginUser
    ) {
        URI location = LocationBuilder.buildLocation(
            reportService.reportCropPost(
                request.toServiceRequest(cropPostId, loginUser.memberId())
            )
        );

        return ResponseEntity.created(location).build();
    }
}
