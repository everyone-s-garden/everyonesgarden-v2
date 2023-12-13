package com.garden.back.garden.dto.response;

import com.garden.back.garden.service.dto.response.GardenByNameResults;

import org.springframework.data.domain.Slice;

public record GardenByNameResponses(
        Slice<GardenSearchResponse> gardenSearchResponses
) {

    public static GardenByNameResponses to(GardenByNameResults gardenByNameResults) {
        return new GardenByNameResponses(
                gardenByNameResults.gardensByName()
                        .map(GardenSearchResponse::to)
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
