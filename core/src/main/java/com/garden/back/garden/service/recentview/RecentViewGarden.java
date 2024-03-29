package com.garden.back.garden.service.recentview;

import com.garden.back.garden.service.dto.response.GardenDetailResult;

import java.util.List;

public record RecentViewGarden(
    Long gardenId,
    Double latitude,
    Double longitude,
    String size,
    String gardenName,
    String price,
    List<String> images,
    String gardenStatus,
    String gardenType
) {
    public static RecentViewGarden to(GardenDetailResult gardenDetail) {
        return new RecentViewGarden(
            gardenDetail.gardenId(),
            gardenDetail.latitude(),
            gardenDetail.longitude(),
            gardenDetail.size(),
            gardenDetail.gardenName(),
            gardenDetail.price(),
            gardenDetail.images(),
            gardenDetail.gardenStatus(),
            gardenDetail.gardenType()
        );
    }
}
