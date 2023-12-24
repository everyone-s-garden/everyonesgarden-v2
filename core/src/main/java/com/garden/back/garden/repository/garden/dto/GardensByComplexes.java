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
            String gardenName,
            String price,
            String imageUrl,
            Double latitude,
            Double longitude,
            String gardenStatus,
            String gardenType
    ) {
    }

}
