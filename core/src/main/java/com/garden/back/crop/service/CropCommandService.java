package com.garden.back.crop.service;

import com.garden.back.crop.infra.MonthlyRecommendedCropsFetcher;
import com.garden.back.crop.service.request.CreateCropsPostServiceRequest;
import com.garden.back.crop.service.request.UpdateCropsPostServiceRequest;
import com.garden.back.crop.service.response.MonthlyRecommendedCropsResponse;
import org.springframework.stereotype.Service;

@Service
public class CropCommandService {

    private final MonthlyRecommendedCropsFetcher monthlyRecommendedCropsFetcher;

    public CropCommandService(MonthlyRecommendedCropsFetcher monthlyRecommendedCropsFetcher) {
        this.monthlyRecommendedCropsFetcher = monthlyRecommendedCropsFetcher;
    }

    public MonthlyRecommendedCropsResponse getMonthlyRecommendedCrops() {
        return MonthlyRecommendedCropsResponse.from(monthlyRecommendedCropsFetcher.getMonthlyRecommendedCrops());
    }

    public Long createCropsPost(CreateCropsPostServiceRequest request) {
        return 1L;
    }

    public void updateCropsPost(Long id, UpdateCropsPostServiceRequest request) {

    }

    public Long addCropsBookmark(Long id, Long aLong) {

        return 1L;
    }

    public void deleteCropsPost(Long id) {
    }

    public void deleteCropsBookmark(Long id, Long aLong) {
    }
}
