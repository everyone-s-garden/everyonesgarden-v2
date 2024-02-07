package com.garden.back.garden.controller.dto.response;

import com.garden.back.garden.service.dto.response.GardenByNameResults;
import java.util.List;

public record GardenByNameResponses(
        List<GardenSearchResponse> gardenSearchResponses,
        boolean hasNext
) {

    public static GardenByNameResponses to(GardenByNameResults gardenByNameResults) {
        return new GardenByNameResponses(
                gardenByNameResults.gardensByName()
                        .stream()
                        .map(GardenSearchResponse::to)
                        .toList(),
                gardenByNameResults.hasNext()
        );
    }

    public record GardenSearchResponse(
            Long gardenId,
            String gardenName,
            String address

    ) {
        public static GardenSearchResponse to(GardenByNameResults.GardenByNameResult garden) {
            return new GardenSearchResponse(
                    garden.gardenId(),
                    garden.gardenName(),
                    garden.address()
            );
        }
    }

}
