package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.garden.dto.GardensByComplexesWithScroll;

import java.util.*;
import java.util.stream.Collectors;

public record GardenByComplexesWithScrollResults(
        Set<GardenByComplexesWithScrollResult> gardenByComplexesWithScrollResults,
        boolean hasNext
) {
    public static GardenByComplexesWithScrollResults of(GardensByComplexesWithScroll gardens) {
        Map<Long, List<String>> gardenAndImages = parseGardenAndImage(gardens);
        return new GardenByComplexesWithScrollResults(
                gardens.gardensByComplexes().stream()
                        .map(gardenByComplexes -> GardenByComplexesWithScrollResult.to(gardenByComplexes, gardenAndImages.get(gardenByComplexes.gardenId()))
                        ).collect(Collectors.toSet()),
                gardens.hasNext()
        );
    }

    private static Map<Long, List<String>> parseGardenAndImage(GardensByComplexesWithScroll gardensGetAll) {
        Map<Long, List<String>> gardenAndImages = new HashMap<>();

        gardensGetAll.gardensByComplexes().forEach(gardenGetAll ->
                gardenAndImages
                        .computeIfAbsent(gardenGetAll.gardenId(), k -> new ArrayList<>())
                        .add(gardenGetAll.imageUrl())
        );

        return gardenAndImages;
    }

    public record GardenByComplexesWithScrollResult(
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
        public static GardenByComplexesWithScrollResult to(GardensByComplexesWithScroll.GardenByComplexesWithScroll garden, List<String> gardenImages) {
            return new GardenByComplexesWithScrollResult(
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
