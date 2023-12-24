package com.garden.back.docs.crop;

import com.garden.back.crop.service.response.MonthlyRecommendedCropsResponse;

import java.util.List;

public class CropFixture {
    public static MonthlyRecommendedCropsResponse createMonthlyRecommendedCropsResponse() {
        MonthlyRecommendedCropsResponse.CropsResponse cropsResponse1 = createCropsResponse(1,
                createCropInfo("파", "파에 대한 설명", "https://link-to-pa"),
                createCropInfo("양파", "양파에 대한 설명", "https://link-to-yangpa"));

        MonthlyRecommendedCropsResponse.CropsResponse cropsResponse2 = createCropsResponse(12,
                createCropInfo("고추", "고추에 대한 설명", "https://link-to-gochu"),
                createCropInfo("감자", "감자에 대한 설명", "https://link-to-gamja"));

        return new MonthlyRecommendedCropsResponse(List.of(cropsResponse1, cropsResponse2));
    }

    private static MonthlyRecommendedCropsResponse.CropsResponse createCropsResponse(int month, MonthlyRecommendedCropsResponse.CropsResponse.CropInfo... cropInfos) {
        return new MonthlyRecommendedCropsResponse.CropsResponse(month, List.of(cropInfos));
    }

    private static MonthlyRecommendedCropsResponse.CropsResponse.CropInfo createCropInfo(String name, String description, String link) {
        return new MonthlyRecommendedCropsResponse.CropsResponse.CropInfo(name, description, link);
    }

}
