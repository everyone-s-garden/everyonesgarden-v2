package com.garden.back.garden.dto.response;

import com.garden.back.garden.service.dto.response.GardenLikeByMemberResults;

import java.util.List;

public record GardenLikeByMemberResponses(
        List<GardenLikeByMemberResponse> gardenLikeByMemberResponses
) {
    public static GardenLikeByMemberResponses to(GardenLikeByMemberResults gardenLikeByMemberResults) {
        return new GardenLikeByMemberResponses(
                gardenLikeByMemberResults.gardenLikeByMemberResults().stream()
                        .map(GardenLikeByMemberResponse::to)
                        .toList()
        );
    }

    public record GardenLikeByMemberResponse(
            Long gardenId,
            String size,
            String gardenName,
            String price,
            String gardenStatus,
            List<String> imageUrls
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
