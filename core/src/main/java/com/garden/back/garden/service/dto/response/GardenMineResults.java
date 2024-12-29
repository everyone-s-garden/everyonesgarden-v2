package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.garden.dto.response.GardenMineRepositoryResponse;
import com.garden.back.garden.repository.garden.dto.response.GardenMineRepositoryResponses;

import java.util.*;

public record GardenMineResults(
    List<GardenMineResult> gardenMineResults,
    Long nextGardenId,
    boolean hasNext
) {
    public static GardenMineResults to(GardenMineRepositoryResponses gardenMineRepositoryResponses) {
        Map<Long, List<String>> gardenAndImages = parseGardenAndImage(gardenMineRepositoryResponses);
        Set<Long> gardenIds = new HashSet<>();
        return new GardenMineResults(
            gardenMineRepositoryResponses.response()
                .stream()
                .filter(gardenMineRepositoryResponse -> gardenIds.add(gardenMineRepositoryResponse.getGardenId()))
                .map(gardenMineRepositoryResponse ->
                    GardenMineResult.to(
                        gardenMineRepositoryResponse,
                        gardenAndImages.get(gardenMineRepositoryResponse.getGardenId())
                    )
                )
                .toList(),
            gardenMineRepositoryResponses.nextGardenId(),
            gardenMineRepositoryResponses.hasNext());
    }

    private static Map<Long, List<String>> parseGardenAndImage(
        GardenMineRepositoryResponses gardenMineRepositoryResponses) {
        Map<Long, List<String>> gardenAndImages = new HashMap<>();

        gardenMineRepositoryResponses.response().forEach(gardenMineRepositoryResponse ->
            gardenAndImages
                .computeIfAbsent(gardenMineRepositoryResponse.getGardenId(), k -> new ArrayList<>())
                .add(gardenMineRepositoryResponse.getImageUrl())
        );

        return gardenAndImages;
    }

    public record GardenMineResult(
        Long gardenId,
        String size,
        String gardenName,
        String price,
        String gardenStatus,
        List<String> imageUrls
    ) {
        public static GardenMineResult to(GardenMineRepositoryResponse response, List<String> imageUrls) {
            return new GardenMineResult(
                response.getGardenId(),
                response.getSize(),
                response.getGardenName(),
                response.getPrice(),
                response.getGardenStatus(),
                imageUrls
            );
        }
    }
}
