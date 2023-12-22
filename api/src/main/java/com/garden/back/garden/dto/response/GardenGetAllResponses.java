package com.garden.back.garden.dto.response;

import com.garden.back.garden.service.dto.response.GardenAllResults;

import java.util.List;

public record GardenGetAllResponses(
        List<GardenGetAllResponse> gardenGetAllResponses,
        boolean hasNext
) {

    public static GardenGetAllResponses to(GardenAllResults gardenAllResults) {
        return new GardenGetAllResponses(
                gardenAllResults.gardenAllResults()
                        .stream()
                        .map(GardenGetAllResponse::to)
                        .toList(),
                gardenAllResults.hasNext()
        );
    }

    public record GardenGetAllResponse(
            Long gardenId,
            Double latitude,
            Double longitude,
            String gardenName,
            String gardenType,
            String price,
            String size,
            String gardenStatus,
            List<String> images
    ) {
        public static GardenGetAllResponse to(GardenAllResults.GardenAllResult result) {
            return new GardenGetAllResponse(
                    result.gardenId(),
                    result.latitude(),
                    result.longitude(),
                    result.gardenName(),
                    result.gardenType(),
                    result.price(),
                    result.size(),
                    result.gardenStatus(),
                    result.images()
            );
        }

    }
}
