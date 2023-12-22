package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.garden.dto.GardensByComplexes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record GardenByComplexesResults(
        List<GardenByComplexesResult> gardenByComplexesResults,
        boolean hasNext
) {
    public static GardenByComplexesResults of(GardensByComplexes gardens) {
        Map<Long, List<String>> gardenAndImages = parseGardenAndImage(gardens);
        return new GardenByComplexesResults(
                gardens.gardensByComplexes().stream()
                        .map(gardenByComplexes -> GardenByComplexesResult.to(gardenByComplexes, gardenAndImages.get(gardenByComplexes.gardenId()))
                        ).toList(),
                gardens.hasNext()
        );
    }

    private static Map<Long, List<String>> parseGardenAndImage(GardensByComplexes gardensGetAll) {
        Map<Long, List<String>> gardenAndImages = new HashMap<>();

        gardensGetAll.gardensByComplexes().forEach(gardenGetAll ->
                gardenAndImages
                        .computeIfAbsent(gardenGetAll.gardenId(), k -> new ArrayList<>())
                        .add(gardenGetAll.imageUrl())
        );

        return gardenAndImages;
    }

    public record GardenByComplexesResult(
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
        public static GardenByComplexesResult to(GardensByComplexes.GardenByComplexes garden, List<String> gardenImages) {
            return new GardenByComplexesResult(
                    garden.gardenId(),
                    garden.size(),
                    garden.gardenName(),
                    garden.price(),
                    gardenImages,
                    garden.gardenStatus(),
                    garden.gardenType(),
                    garden.latitude(),
                    garden.longitude()
            );
        }
    }
}
