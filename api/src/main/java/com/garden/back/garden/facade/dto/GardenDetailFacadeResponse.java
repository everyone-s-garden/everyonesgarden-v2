package com.garden.back.garden.facade.dto;

import com.garden.back.garden.service.dto.response.GardenDetailResult;

import java.util.List;

public record GardenDetailFacadeResponse (
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

    public static GardenDetailFacadeResponse to(
        GardenDetailResult result,
        Long roomId){
        return new GardenDetailFacadeResponse(
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
            roomId,
            result.openAPIGardenId()
        );
    }
}
