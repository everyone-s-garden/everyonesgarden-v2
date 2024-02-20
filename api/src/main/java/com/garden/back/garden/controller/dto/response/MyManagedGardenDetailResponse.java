package com.garden.back.garden.controller.dto.response;

import com.garden.back.garden.service.dto.response.MyManagedGardenDetailResult;

import java.util.List;

public record MyManagedGardenDetailResponse(
    Long myManagedGardenId,
    String gardenName,
    String address,
    String useStartDate,
    String useEndDate,
    List<String> images,
    String description
) {
    public static MyManagedGardenDetailResponse to(MyManagedGardenDetailResult result) {
        return new MyManagedGardenDetailResponse(
            result.myManagedGardenId(),
            result.gardenName(),
            result.address(),
            result.useStartDate(),
            result.useEndDate(),
            List.of(result.imageUrl()),
            result.description()
        );
    }
}
