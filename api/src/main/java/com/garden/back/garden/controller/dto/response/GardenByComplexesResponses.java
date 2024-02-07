package com.garden.back.garden.dto.response;

import com.garden.back.garden.service.dto.response.GardenByComplexesResults;

import java.util.List;

public record GardenByComplexesResponses(
        List<GardenByComplexesResponse> gardenByComplexesResponses,
        boolean hasNext
) {
    public static GardenByComplexesResponses to(GardenByComplexesResults results) {
        return new GardenByComplexesResponses(
                results.gardenByComplexesResults().stream()
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
        public static GardenByComplexesResponse to(GardenByComplexesResults.GardenByComplexesResult result) {
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
