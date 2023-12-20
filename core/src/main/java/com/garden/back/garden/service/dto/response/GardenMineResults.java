package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.garden.dto.response.GardenMineRepositoryResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record GardenMineResults(
        List<GardenMineResult> gardenMineResults
) {
    public static GardenMineResults to(List<GardenMineRepositoryResponse> gardenMineRepositoryResponses) {
        Map<Long, List<String>> gardenAndImages = parseGardenAndImage(gardenMineRepositoryResponses);
        return new GardenMineResults(
                gardenMineRepositoryResponses.stream()
                        .map(gardenMineRepositoryResponse ->
                                GardenMineResult.to(
                                        gardenMineRepositoryResponse,
                                        gardenAndImages.get(gardenMineRepositoryResponse.getGardenId())
                                )
                        )
                        .toList());

    }

    private static Map<Long, List<String>> parseGardenAndImage(List<GardenMineRepositoryResponse> GardenMineRepositoryResponses) {
        Map<Long, List<String>> gardenAndImages = new HashMap<>();

        GardenMineRepositoryResponses.forEach(gardenMineRepositoryResponse ->
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
