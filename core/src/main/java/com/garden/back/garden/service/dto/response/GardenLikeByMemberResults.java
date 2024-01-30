package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.garden.dto.response.GardenLikeByMemberRepositoryResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record GardenLikeByMemberResults(
        List<GardenLikeByMemberResult> gardenLikeByMemberResults
) {
    public static GardenLikeByMemberResults to(List<GardenLikeByMemberRepositoryResponse> gardenLikeByMemberRepositoryResponses) {
        Map<Long, List<String>> gardenAndImages = parseGardenAndImage(gardenLikeByMemberRepositoryResponses);
        return new GardenLikeByMemberResults(
                gardenLikeByMemberRepositoryResponses.stream()
                        .map(gardenLikeByMemberRepositoryResponse
                                -> GardenLikeByMemberResult.to(
                                gardenLikeByMemberRepositoryResponse,
                                gardenAndImages.get(gardenLikeByMemberRepositoryResponse.getGardenId()))
                        )
                        .toList()
        );
    }

    private static Map<Long, List<String>> parseGardenAndImage(List<GardenLikeByMemberRepositoryResponse> gardenLikeByMemberRepositoryResponses) {
        Map<Long, List<String>> gardenAndImages = new HashMap<>();

        gardenLikeByMemberRepositoryResponses.forEach(gardenLikeByMemberRepositoryResponse ->
                gardenAndImages
                        .computeIfAbsent(gardenLikeByMemberRepositoryResponse.getGardenId(), k -> new ArrayList<>())
                        .add(gardenLikeByMemberRepositoryResponse.getImageUrl())
        );

        return gardenAndImages;
    }

    public record GardenLikeByMemberResult(
            Long gardenId,
            String size,
            String gardenName,
            String price,
            String gardenStatus,
            List<String> imageUrls
    ) {
        public static GardenLikeByMemberResult to(GardenLikeByMemberRepositoryResponse response, List<String> imageUrls) {
            return new GardenLikeByMemberResult(
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
