package com.garden.back.crop;

import com.garden.back.crop.service.response.MonthlyRecommendedCropsResponse;
import com.garden.back.crop.service.CropService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/crops", produces = MediaType.APPLICATION_JSON_VALUE)
public class CropController {

    private final CropService cropService;

    public CropController(CropService cropService) {
        this.cropService = cropService;
    }

    @GetMapping
    public ResponseEntity<MonthlyRecommendedCropsResponse> getMonthlyRecommendedCrops() {
        return ResponseEntity.ok(cropService.getMonthlyRecommendedCrops());
    }
    
}
