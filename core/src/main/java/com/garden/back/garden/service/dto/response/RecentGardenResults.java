package com.garden.back.garden.service.dto.response;

import com.garden.back.garden.service.recentview.RecentViewGarden;
import com.garden.back.garden.service.recentview.RecentViewGardens;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record RecentGardenResults(
        List<RecentGardenResult> recentGardenResults
) {
    public static RecentGardenResults to(Optional<RecentViewGardens> recentViewGardens) {
        return recentViewGardens.map(viewGardens -> new RecentGardenResults(
            viewGardens.getRecentViewGardens().recentViewGardens().stream()
                .map(RecentGardenResult::to)
                .toList()
        )).orElseGet(() -> new RecentGardenResults(Collections.emptyList()));
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
