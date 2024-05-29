package com.garden.back.garden.controller.dto.response;

import com.garden.back.garden.service.dto.response.GardenByComplexesWithScrollResults;

import java.util.List;

public record GardenByComplexesWithScrollResponses(
        List<GardenByComplexesWithScrollResponse> gardenByComplexesWithScrollResponses,
        boolean hasNext
) {
    public static GardenByComplexesWithScrollResponses to(GardenByComplexesWithScrollResults results) {
        return new GardenByComplexesWithScrollResponses(
                results.gardenByComplexesWithScrollResults().stream()
                        .map(GardenByComplexesWithScrollResponse::to)
                        .toList(),
                results.hasNext()
        );
    }

    public record GardenByComplexesWithScrollResponse(
            Long gardenId,
            String size,
            String gardenName,
            String price,
            List<String> images,
            String gardenStatus,
            String gardenType,
            Double latitude,
            Double longitude

    ) {
        public static GardenByComplexesWithScrollResponse to(GardenByComplexesWithScrollResults.GardenByComplexesWithScrollResult result) {
            return new GardenByComplexesWithScrollResponse(
                    result.gardenId(),
                    result.size(),
                    result.gardenName(),
                    result.price(),
                    result.images(),
                    result.gardenStatus(),
                    result.gardenType(),
                    result.latitude(),
                    result.longitude()
            );
        }

    }

}
