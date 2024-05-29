package com.garden.back.garden.controller.dto.response;

import com.garden.back.garden.service.dto.response.GardenByComplexesWithScrollResults;

import java.util.List;

public record GardenByComplexesResponses(
        List<GardenByComplexesResponse> gardenByComplexesResponses,
        boolean hasNext
) {
    public static GardenByComplexesResponses to(GardenByComplexesWithScrollResults results) {
        return new GardenByComplexesResponses(
                results.gardenByComplexesWithScrollResults().stream()
                        .map(GardenByComplexesResponse::to)
                        .toList(),
                results.hasNext()
        );
    }

    public record GardenByComplexesResponse(
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
        public static GardenByComplexesResponse to(GardenByComplexesWithScrollResults.GardenByComplexesWithScrollResult result) {
            return new GardenByComplexesResponse(
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
