package com.garden.back.garden.controller.dto.response;

import com.garden.back.garden.service.dto.response.RecentCreatedGardenResults;

import java.util.List;

public record RecentCreatedGardenResponses(
    List<RecentCreatedGardenResponse> recentCreatedGardenResponses
) {
    public static RecentCreatedGardenResponses to(RecentCreatedGardenResults results) {
        return new RecentCreatedGardenResponses(results.recentCreatedGardenResults().stream()
            .map(RecentCreatedGardenResponse::to)
            .toList());
    }

    public record RecentCreatedGardenResponse(
        Long gardenId,
        String gardenName,
        String address,
        String recruitStartDate,
        String recruitEndDate,
        String price,
        boolean isLiked,
        String imageUrl
    ) {
        public static RecentCreatedGardenResponse to(RecentCreatedGardenResults.RecentCreatedGardenResult result) {
            return new RecentCreatedGardenResponse(
                result.gardenId(),
                result.gardenName(),
                result.address(),
                result.recruitStartDate(),
                result.recruitEndDate(),
                result.price(),
                result.isLiked(),
                result.imageUrl()
            );
        }
    }
}
