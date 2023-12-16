package com.garden.back.crop.service.response;

import com.garden.back.crop.infra.MonthlyRecommendedCropsInfraResponse;

import java.util.List;

public record MonthlyRecommendedCropsResponse(
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

    public static MonthlyRecommendedCropsResponse from(MonthlyRecommendedCropsInfraResponse infraResponse) {
        List<CropsResponse> cropsResponses = infraResponse.cropsResponses().stream()
            .map(infraCropsResponse -> new CropsResponse(
                    infraCropsResponse.month(),
                    infraCropsResponse.cropInfos().stream()
                        .map(infraCropInfo -> new CropsResponse.CropInfo(
                                infraCropInfo.name(),
                                infraCropInfo.description(),
                                infraCropInfo.link()
                            )
                        ).toList()
                )
            ).toList();

        return new MonthlyRecommendedCropsResponse(cropsResponses);
    }
}
