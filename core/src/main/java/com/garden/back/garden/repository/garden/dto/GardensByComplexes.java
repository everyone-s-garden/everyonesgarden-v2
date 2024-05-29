package com.garden.back.garden.repository.garden.dto;

import java.util.List;

public record GardensByComplexes(
    List<GardenByComplexes> gardensByComplexes
) {
    public static GardensByComplexes of(
        List<GardenByComplexes> gardensByComplexes
    ) {
        return new GardensByComplexes(
            gardensByComplexes
        );
    }

    public record GardenByComplexes(
        Long gardenId,
        String size,
        String price,
        String gardenStatus,
        String gardenName,
        Double latitude,
        Double longitude,
        String imageUrl,
        String gardenType
    ) {
    }

}

