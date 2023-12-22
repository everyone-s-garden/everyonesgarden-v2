package com.garden.back.crop.service;

import com.garden.back.crop.infra.MonthlyRecommendedCropsFetcher;
import com.garden.back.crop.service.response.MonthlyRecommendedCropsResponse;
import org.springframework.stereotype.Service;

@Service
public class CropService {

    private final MonthlyRecommendedCropsFetcher monthlyRecommendedCropsFetcher;

    public CropService(MonthlyRecommendedCropsFetcher monthlyRecommendedCropsFetcher) {
        this.monthlyRecommendedCropsFetcher = monthlyRecommendedCropsFetcher;
    }

    public MonthlyRecommendedCropsResponse getMonthlyRecommendedCrops() {
        return MonthlyRecommendedCropsResponse.from(monthlyRecommendedCropsFetcher.getMonthlyRecommendedCrops());
    }
}
