package com.garden.back.garden.controller.dto.response;

import com.garden.back.garden.facade.dto.GardenDetailFacadeResponse;

import java.util.List;

public record GardenDetailResponse(
    Long gardenId,
    String address,
    Double latitude,
    Double longitude,
    String gardenName,
    String gardenType,
    String price,
    String contact,
    String size,
    String gardenStatus,
    Long writerId,
    String recruitStartDate,
    String recruitEndDate,
    String gardenDescription,
    List<String> images,
    String gardenFacilities,
    Long gardenLikeId,
    Long roomId,
    String openAPIResourceId
) {
    public static GardenDetailResponse to(GardenDetailFacadeResponse result) {
        return new GardenDetailResponse(
            result.gardenId(),
            result.address(),
            result.latitude(),
            result.longitude(),
            result.gardenName(),
            result.gardenType(),
            result.price(),
            result.contact(),
            result.size(),
            result.gardenStatus(),
            result.writerId(),
            result.recruitStartDate(),
            result.recruitEndDate(),
            result.gardenDescription(),
            result.images(),
            result.gardenFacilities(),
            result.gardenLikeId(),
            result.roomId(),
            result.openAPIResourceId()
        );
    }
}
