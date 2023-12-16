package com.garden.back.crop.infra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public record MonthlyRecommendedCropsInfraResponse(
    List<CropsResponse> cropsResponses
) {
    public record CropsResponse(
        Integer month,
        List<CropInfo> cropInfos
    ) {
        public record CropInfo(
            String name,
            String description,
            String link
        ) {}
    }

    public static MonthlyRecommendedCropsInfraResponse from(Map<Integer, List<CropsResponse.CropInfo>> monthlyCropInfos) {
        List<CropsResponse> cropsResponses = new ArrayList<>();
        for (int month = 1; month <= 12; month++) {
            List<CropsResponse.CropInfo> cropInfos = monthlyCropInfos.getOrDefault(month, new ArrayList<>());
            cropsResponses.add(new CropsResponse(month, cropInfos));
        }
        return new MonthlyRecommendedCropsInfraResponse(cropsResponses);
    }
}
