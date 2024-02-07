package com.garden.back.garden.dto.response;

import com.garden.back.garden.service.dto.response.RecentGardenResults;

import java.util.List;

public record RecentGardenResponses(
        List<RecentGardenResponse> recentGardenResponses
) {
    public static RecentGardenResponses to(RecentGardenResults recentGardenResults) {
        return new RecentGardenResponses(
                recentGardenResults.recentGardenResults().stream()
                        .map(RecentGardenResponse::to)
                        .toList()
        );
    }

    public record RecentGardenResponse(
            Long gardenId,
            String size,
            String gardenName,
            String price,
            List<String> images,
            String gardenStatus,
            String gardenType
    ) {
        public static RecentGardenResponse to(RecentGardenResults.RecentGardenResult result) {
            return new RecentGardenResponse(
                    result.gardenId(),
                    result.size(),
                    result.gardenName(),
                    result.price(),
                    result.images(),
                    result.gardenStatus(),
                    result.gardenType()
            );
        }

    }
}
