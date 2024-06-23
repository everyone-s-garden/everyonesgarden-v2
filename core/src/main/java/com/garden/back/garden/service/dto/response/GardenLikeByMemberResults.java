package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.repository.garden.dto.response.GardenLikeByMemberRepositoryResponse;
import com.garden.back.garden.repository.garden.dto.response.GardenLikeByMemberRepositoryResponses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record GardenLikeByMemberResults(
        List<GardenLikeByMemberResult> gardenLikeByMemberResults,
        Long nextGardenId,
        Boolean hasNext
) {
    public static GardenLikeByMemberResults to(GardenLikeByMemberRepositoryResponses gardenLikeByMemberRepositoryResponses) {
        Map<Long, List<String>> gardenAndImages = parseGardenAndImage(gardenLikeByMemberRepositoryResponses);
        return new GardenLikeByMemberResults(
                gardenLikeByMemberRepositoryResponses.response().stream()
                        .map(gardenLikeByMemberRepositoryResponse
                                -> GardenLikeByMemberResult.to(
                                gardenLikeByMemberRepositoryResponse,
                                gardenAndImages.get(gardenLikeByMemberRepositoryResponse.getGardenId()))
                        )
                        .toList(),
            gardenLikeByMemberRepositoryResponses.nextGardenId(),
            gardenLikeByMemberRepositoryResponses.hasNext()
        );
    }

    private static Map<Long, List<String>> parseGardenAndImage(GardenLikeByMemberRepositoryResponses gardenLikeByMemberRepositoryResponses) {
        Map<Long, List<String>> gardenAndImages = new HashMap<>();

        gardenLikeByMemberRepositoryResponses.response().forEach(gardenLikeByMemberRepositoryResponse ->
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
