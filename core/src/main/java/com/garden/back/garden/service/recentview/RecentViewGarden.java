package com.garden.back.garden.service.recentview;

import com.garden.back.garden.service.dto.response.GardenDetailResult;

public record RecentViewGarden(
        Long gardenId,
        String size,
        String gardenName,
        String price,
        String images,
        String gardenStatus,
        String gardenType
) {
    public static RecentViewGarden to(GardenDetailResult gardenDetail) {
        return new RecentViewGarden(
                gardenDetail.gardenId(),
                gardenDetail.size(),
                gardenDetail.gardenName(),
                gardenDetail.price(),
                gardenDetail.images().get(0),
                gardenDetail.gardenStatus(),
                gardenDetail.gardenType()
        );
    }
}
