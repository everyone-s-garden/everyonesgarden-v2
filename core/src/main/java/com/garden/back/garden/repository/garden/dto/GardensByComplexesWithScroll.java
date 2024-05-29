package com.garden.back.garden.repository.garden.dto;

import java.util.List;

public record GardensByComplexesWithScroll(
        List<GardenByComplexesWithScroll> gardensByComplexes,
        boolean hasNext
) {
    public static GardensByComplexesWithScroll of(
            List<GardenByComplexesWithScroll> gardensByComplexes,
            boolean hasNext
    ) {
        return new GardensByComplexesWithScroll(
                gardensByComplexes,
                hasNext
        );
    }

    public record GardenByComplexesWithScroll(
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
