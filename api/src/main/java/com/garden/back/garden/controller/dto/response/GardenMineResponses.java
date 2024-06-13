package com.garden.back.garden.controller.dto.response;

import com.garden.back.garden.service.dto.response.GardenMineResults;

import java.util.List;

public record GardenMineResponses(
        List<GardenMineResponse> gardenMineResponses,
        Long nextGardenId,
        boolean hasNext
) {
    public static GardenMineResponses to(GardenMineResults gardenMineResults) {
        return new GardenMineResponses(
                gardenMineResults.gardenMineResults().stream()
                        .map(GardenMineResponse::to)
                        .toList(),
            gardenMineResults.nextGardenId(),
            gardenMineResults.hasNext()
        );
    }

    public record GardenMineResponse(
            Long gardenId,
            String size,
            String gardenName,
            String price,
            String gardenStatus,
            List<String> images
    ) {
        public static GardenMineResponse to(GardenMineResults.GardenMineResult gardenMineResult) {
            return new GardenMineResponse(
                    gardenMineResult.gardenId(),
                    gardenMineResult.size(),
                    gardenMineResult.gardenName(),
                    gardenMineResult.price(),
                    gardenMineResult.gardenStatus(),
                    gardenMineResult.imageUrls()
            );
        }
    }

}
