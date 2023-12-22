package com.garden.back.report;

import com.garden.back.report.request.ReportGardenRequest;
import com.garden.back.report.service.ReportService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping(path = "/{gardenId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> reportGarden(@PathVariable("gardenId") Long gardenId, @RequestBody @Valid ReportGardenRequest request) {
        Long userId = 1L;
        URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(reportService.reportGarden(gardenId, userId, request.toServiceRequest()))
            .toUri();

        return ResponseEntity.created(location).build();
    }
}
