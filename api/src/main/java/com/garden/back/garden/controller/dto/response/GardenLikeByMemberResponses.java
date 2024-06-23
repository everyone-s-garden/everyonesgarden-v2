package com.garden.back.garden.controller.dto.response;

import com.garden.back.garden.service.dto.response.GardenLikeByMemberResults;

import java.util.List;

public record GardenLikeByMemberResponses(
    List<GardenLikeByMemberResponse> gardenLikeByMemberResponses,
    Long nextGardenId,
    Boolean hasNext
) {
    public static GardenLikeByMemberResponses to(GardenLikeByMemberResults gardenLikeByMemberResults) {
        return new GardenLikeByMemberResponses(
            gardenLikeByMemberResults.gardenLikeByMemberResults().stream()
                .map(GardenLikeByMemberResponse::to)
                .toList(),
            gardenLikeByMemberResults.nextGardenId(),
            gardenLikeByMemberResults.hasNext()
        );
    }

    public record GardenLikeByMemberResponse(
        Long gardenId,
        String size,
        String gardenName,
        String price,
        String gardenStatus,
        List<String> images
    ) {
        public static GardenLikeByMemberResponse to(GardenLikeByMemberResults.GardenLikeByMemberResult gardenLikeByMemberResult) {
            return new GardenLikeByMemberResponse(
                gardenLikeByMemberResult.gardenId(),
                gardenLikeByMemberResult.size(),
                gardenLikeByMemberResult.gardenName(),
                gardenLikeByMemberResult.price(),
                gardenLikeByMemberResult.gardenStatus(),
                gardenLikeByMemberResult.imageUrls()
            );
        }

    }
}
