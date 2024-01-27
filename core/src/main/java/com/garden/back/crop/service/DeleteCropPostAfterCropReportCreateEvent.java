package com.garden.back.crop.service;

import com.garden.back.report.domain.crop.CropPostReportCreateEvent;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class DeleteCropPostAfterCropReportCreateEvent {

    private final CropCommandService cropCommandService;

    public DeleteCropPostAfterCropReportCreateEvent(CropCommandService cropCommandService) {
        this.cropCommandService = cropCommandService;
    }

    @TransactionalEventListener
    @Async
    public void deleteCropPost(CropPostReportCreateEvent event) {
        Long cropPostId = event.cropPostReport().getCropPostId();
        cropCommandService.deleteCropPostByReport(cropPostId);
    }
}
