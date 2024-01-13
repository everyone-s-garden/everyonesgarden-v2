package com.garden.back.garden.repository.garden.dto;

import java.util.List;

public record GardensByComplexes(
        List<GardenByComplexes> gardensByComplexes,
        boolean hasNext
) {
    public static GardensByComplexes of(
            List<GardenByComplexes> gardensByComplexes,
            boolean hasNext
    ) {
        return new GardensByComplexes(
                gardensByComplexes,
                hasNext
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
