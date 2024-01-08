package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.service.recentview.RecentViewGarden;
import com.garden.back.garden.service.recentview.RecentViewGardens;

import java.util.List;

public record RecentGardenResults(
        List<RecentGardenResult> recentGardenResults
) {
    public static RecentGardenResults to(RecentViewGardens recentViewGardens) {
        return new RecentGardenResults(
                recentViewGardens.getRecentViewGardens().recentViewGardens().stream()
                        .map(RecentGardenResult::to)
                        .toList()
        );
    }

    public record RecentGardenResult(
            Long gardenId,
            String size,
            String gardenName,
            String price,
            List<String> images,
            String gardenStatus,
            String gardenType
    ) {
        public static RecentGardenResult to(RecentViewGarden recentViewGarden) {
            return new RecentGardenResult(
                    recentViewGarden.gardenId(),
                    recentViewGarden.size(),
                    recentViewGarden.gardenName(),
                    recentViewGarden.price(),
                    recentViewGarden.images(),
                    recentViewGarden.gardenStatus(),
                    recentViewGarden.gardenType()
            );
        }
    }
}
